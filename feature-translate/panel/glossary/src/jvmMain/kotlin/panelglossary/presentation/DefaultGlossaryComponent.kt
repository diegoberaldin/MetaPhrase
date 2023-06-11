package panelglossary.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.GlossaryTermModel
import data.LanguageModel
import glossaryrepository.GlossaryTermRepository
import glossaryusecase.GetGlossaryTermsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import language.repo.FlagsRepository
import language.repo.LanguageRepository
import repository.repo.SegmentRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultGlossaryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val flagsRepository: FlagsRepository,
    private val segmentRepository: SegmentRepository,
    private val glossaryTermRepository: GlossaryTermRepository,
    private val getGlossaryTerms: GetGlossaryTermsUseCase,
) : GlossaryComponent, ComponentContext by componentContext {

    private var lastSourceLanguage: LanguageModel? = null
    private var lastTargetLanguage: LanguageModel? = null
    private var lastSourceMessage: String? = null
    private val isLoading = MutableStateFlow(false)
    private val isBaseLanguage = MutableStateFlow(false)
    private val sourceFlag = MutableStateFlow("")
    private val targetFlag = MutableStateFlow("")
    private val terms = MutableStateFlow<List<Pair<GlossaryTermModel, List<GlossaryTermModel>>>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<GlossaryUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    isLoading,
                    isBaseLanguage,
                    sourceFlag,
                    targetFlag,
                    terms,
                ) { isLoading, isBaseLanguage, sourceFlag, targetFlag, terms ->
                    GlossaryUiState(
                        isLoading = isLoading,
                        isBaseLanguage = isBaseLanguage,
                        sourceFlag = sourceFlag,
                        targetFlag = targetFlag,
                        terms = terms,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = GlossaryUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun clear() {
        terms.value = emptyList()
    }

    override fun loadGlossaryTerms(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            val sourceLanguage = languageRepository.getBase(projectId) ?: return@launch
            val targetLanguage = languageRepository.getById(languageId) ?: return@launch
            val sourceSegment = segmentRepository.getByKey(key, languageId = sourceLanguage.id)
            val sourceMessage = sourceSegment?.text.orEmpty()

            lastSourceLanguage = sourceLanguage
            lastTargetLanguage = targetLanguage
            lastSourceMessage = sourceMessage
            isBaseLanguage.value = sourceLanguage == targetLanguage
            sourceFlag.value = flagsRepository.getFlag(sourceLanguage.code)
            targetFlag.value = flagsRepository.getFlag(targetLanguage.code)

            innerReload()
        }
    }

    private suspend fun innerReload() {
        val sourceLanguage = lastSourceLanguage ?: return
        val targetLanguage = lastTargetLanguage ?: return
        val message = lastSourceMessage.orEmpty()
        isLoading.value = true
        terms.value =
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
        isLoading.value = false
    }

    override fun addSourceTerm(lemma: String) {
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

    override fun addTargetTerm(lemma: String, source: GlossaryTermModel) {
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

    override fun deleteTerm(term: GlossaryTermModel) {
        viewModelScope.launch(dispatchers.io) {
            glossaryTermRepository.delete(term)
            innerReload()
        }
    }
}
