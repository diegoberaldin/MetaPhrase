package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.Constants
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.InvalidPlaceholderReference
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.SpellingMistakeReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultValidateComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : ValidateComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override val selectionEvents = MutableSharedFlow<String>()
    override val uiState = MutableStateFlow(InvalidSegmentUiState())

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

    override fun loadInvalidPlaceholders(projectId: Int, languageId: Int, invalidKeys: List<String>) {
        viewModelScope.launch(dispatchers.io) {
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            val references = invalidKeys.mapNotNull { key ->
                val target = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@mapNotNull null
                val source =
                    segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: return@mapNotNull null

                val sourcePlaceholders = extractPlaceholders(source.text)
                val targetPlaceholders = extractPlaceholders(target.text)
                val exceeding = (targetPlaceholders - sourcePlaceholders.toSet()).toList()
                val missing = (sourcePlaceholders - targetPlaceholders.toSet()).toList()

                InvalidPlaceholderReference(key = key, extraPlaceholders = exceeding, missingPlaceholders = missing)
            }
            uiState.update {
                it.copy(content = ValidationContent.InvalidPlaceholders(references = references))
            }
        }
    }

    private fun extractPlaceholders(message: String): List<String> {
        val res = Constants.PlaceholderRegex.findAll(message).map { it.value }.toList()
        return res + Constants.NamedPlaceholderRegex.findAll(message).map { it.value }.toList()
    }

    override fun loadSpellingMistakes(errors: Map<String, List<String>>) {
        val references = errors.keys.map {
            SpellingMistakeReference(
                key = it,
                mistakes = errors[it].orEmpty(),
            )
        }
        uiState.update {
            it.copy(content = ValidationContent.SpellingMistakes(references = references))
        }
    }

    override fun clear() {
        uiState.update { it.copy(content = null) }
    }

    override fun selectItem(value: Int) {
        when (val content = uiState.value.content) {
            is ValidationContent.InvalidPlaceholders -> {
                val reference = content.references[value]
                viewModelScope.launch(dispatchers.io) {
                    selectionEvents.emit(reference.key)
                }
            }

            is ValidationContent.SpellingMistakes -> {
                val reference = content.references[value]
                viewModelScope.launch(dispatchers.io) {
                    selectionEvents.emit(reference.key)
                }
            }

            else -> Unit
        }
    }
}
