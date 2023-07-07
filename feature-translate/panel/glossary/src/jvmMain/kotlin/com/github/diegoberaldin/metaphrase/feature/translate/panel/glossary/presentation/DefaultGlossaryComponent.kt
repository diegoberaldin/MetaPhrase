package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.GetGlossaryTermsUseCase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.FlagsRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultGlossaryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<GlossaryComponent.Intent, GlossaryComponent.UiState, GlossaryComponent.Effect> = DefaultMviModel(
        GlossaryComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val flagsRepository: FlagsRepository,
    private val segmentRepository: SegmentRepository,
    private val glossaryTermRepository: GlossaryTermRepository,
    private val getGlossaryTerms: GetGlossaryTermsUseCase,
) : GlossaryComponent,
    MviModel<GlossaryComponent.Intent, GlossaryComponent.UiState, GlossaryComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private var lastSourceLanguage: LanguageModel? = null
    private var lastTargetLanguage: LanguageModel? = null
    private var lastSourceMessage: String? = null
    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: GlossaryComponent.Intent) {
        when (intent) {
            is GlossaryComponent.Intent.AddSourceTerm -> addSourceTerm(intent.lemma)
            is GlossaryComponent.Intent.AddTargetTerm -> addTargetTerm(lemma = intent.lemma, source = intent.source)
            GlossaryComponent.Intent.Clear -> clear()
            is GlossaryComponent.Intent.DeleteTerm -> deleteTerm(intent.term)
            is GlossaryComponent.Intent.Load -> load(
                key = intent.key,
                projectId = intent.projectId,
                languageId = intent.languageId,
            )
        }
    }

    private fun clear() {
        mvi.updateState { it.copy(terms = emptyList()) }
    }

    private fun load(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            val sourceLanguage = languageRepository.getBase(projectId) ?: return@launch
            val targetLanguage = languageRepository.getById(languageId) ?: return@launch
            val sourceSegment = segmentRepository.getByKey(key, languageId = sourceLanguage.id)
            val sourceMessage = sourceSegment?.text.orEmpty()

            lastSourceLanguage = sourceLanguage
            lastTargetLanguage = targetLanguage
            lastSourceMessage = sourceMessage
            mvi.updateState {
                it.copy(
                    isBaseLanguage = sourceLanguage == targetLanguage,
                    sourceFlag = flagsRepository.getFlag(sourceLanguage.code),
                    targetFlag = flagsRepository.getFlag(targetLanguage.code),
                )
            }

            innerReload()
        }
    }

    private suspend fun innerReload() {
        val sourceLanguage = lastSourceLanguage ?: return
        val targetLanguage = lastTargetLanguage ?: return
        val message = lastSourceMessage.orEmpty()
        mvi.updateState { it.copy(isLoading = true) }
        val terms =
            getGlossaryTerms(
                message = message.lowercase(),
                lang = sourceLanguage.code,
            ).map { model ->
                val targetTerms = glossaryTermRepository.getAssociated(
                    model = model,
                    otherLang = targetLanguage.code,
                )
                model to targetTerms
            }
        mvi.updateState {
            it.copy(
                terms = terms,
                isLoading = false,
            )
        }
    }

    private fun addSourceTerm(lemma: String) {
        val langCode = lastSourceLanguage?.code ?: return
        val trimmedLemma = lemma.trim()
        viewModelScope.launch(dispatchers.io) {
            val existing = glossaryTermRepository.get(lemma = trimmedLemma, lang = langCode)
            if (existing == null) {
                val term = GlossaryTermModel(lemma = trimmedLemma, lang = langCode)
                glossaryTermRepository.create(term)
            }

            innerReload()
        }
    }

    private fun addTargetTerm(lemma: String, source: GlossaryTermModel) {
        val langCode = lastTargetLanguage?.code ?: return
        val trimmedLemma = lemma.trim()
        viewModelScope.launch(dispatchers.io) {
            val existing = glossaryTermRepository.get(lemma = trimmedLemma, lang = langCode)
            val targetId = if (existing == null) {
                val term = GlossaryTermModel(lemma = trimmedLemma, lang = langCode)
                glossaryTermRepository.create(term)
            } else {
                existing.id
            }
            val sourceId = source.id
            if (!glossaryTermRepository.areAssociated(sourceId = sourceId, targetId = targetId)) {
                glossaryTermRepository.associate(sourceId = sourceId, targetId = targetId)
            }

            innerReload()
        }
    }

    private fun deleteTerm(term: GlossaryTermModel) {
        viewModelScope.launch(dispatchers.io) {
            glossaryTermRepository.delete(term)
            innerReload()
        }
    }
}
