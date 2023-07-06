package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultNewSegmentComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<NewSegmentComponent.ViewIntent, NewSegmentComponent.UiState, NewSegmentComponent.Effect> = DefaultMviModel(
        NewSegmentComponent.UiState(),
    ),
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : NewSegmentComponent,
    MviModel<NewSegmentComponent.ViewIntent, NewSegmentComponent.UiState, NewSegmentComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override var projectId = 0
    override lateinit var language: LanguageModel

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

    override fun reduce(intent: NewSegmentComponent.ViewIntent) {
        when (intent) {
            NewSegmentComponent.ViewIntent.Close -> close()
            is NewSegmentComponent.ViewIntent.SetKey -> setKey(intent.value)
            is NewSegmentComponent.ViewIntent.SetText -> setText(intent.value)
            NewSegmentComponent.ViewIntent.Submit -> submit()
        }
    }

    private fun setKey(value: String) {
        mvi.updateState { it.copy(key = value) }
    }

    private fun setText(value: String) {
        mvi.updateState { it.copy(text = value) }
    }

    private fun close() {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(NewSegmentComponent.Effect.Done(null))
        }
    }

    private fun submit() {
        mvi.updateState {
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
                mvi.updateState { it.copy(keyError = "message_missing_field".localized()) }
                valid = false
            } else {
                val existing = segmentRepository.getByKey(key = key, languageId = language.id)
                if (existing != null) {
                    mvi.updateState { it.copy(keyError = "message_duplicate_key".localized()) }
                    valid = false
                }
            }
            if (text.isEmpty()) {
                mvi.updateState { it.copy(textError = "message_missing_field".localized()) }
                valid = false
            }
            if (!valid) {
                return@launch
            }

            mvi.updateState { it.copy(isLoading = true) }
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

            mvi.updateState { it.copy(isLoading = false) }
            mvi.emitEffect(NewSegmentComponent.Effect.Done(res.copy(id = id)))
        }
    }
}
