package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultNewSegmentComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : NewSegmentComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(NewSegmentUiState())
    override val done = MutableSharedFlow<SegmentModel?>()
    override lateinit var language: LanguageModel
    override var projectId = 0

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

    override fun setKey(value: String) {
        uiState.update { it.copy(key = value) }
    }

    override fun setText(value: String) {
        uiState.update { it.copy(text = value) }
    }

    override fun close() {
        viewModelScope.launch(dispatchers.io) {
            done.emit(null)
        }
    }

    override fun submit() {
        uiState.update {
            it.copy(
                keyError = "",
                textError = "",
            )
        }
        val key = uiState.value.key.trim()
        val text = uiState.value.text.trim()
        var valid = true
        viewModelScope.launch(dispatchers.io) {
            if (key.isEmpty()) {
                uiState.update { it.copy(keyError = "message_missing_field".localized()) }
                valid = false
            } else {
                val existing = segmentRepository.getByKey(key = key, languageId = language.id)
                if (existing != null) {
                    uiState.update { it.copy(keyError = "message_duplicate_key".localized()) }
                    valid = false
                }
            }
            if (text.isEmpty()) {
                uiState.update { it.copy(textError = "message_missing_field".localized()) }
                valid = false
            }
            if (!valid) {
                return@launch
            }

            uiState.update { it.copy(isLoading = true) }
            val res = SegmentModel(
                key = key,
                text = text,
            )
            val id = segmentRepository.create(model = res, languageId = language.id)

            // ensures segment is present in other languages
            val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }
            for (lang in otherLanguages) {
                val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                if (existing == null) {
                    segmentRepository.create(model = SegmentModel(key = key), languageId = lang.id)
                }
            }

            uiState.update { it.copy(isLoading = false) }
            done.emit(res.copy(id = id))
        }
    }
}
