package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.GetSimilaritiesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultTranslationMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<TranslationMemoryComponent.ViewIntent, TranslationMemoryComponent.UiState, TranslationMemoryComponent.Effect> = DefaultMviModel(
        TranslationMemoryComponent.UiState(),
    ),
    private val segmentRepository: SegmentRepository,
    private var getSimilarities: GetSimilaritiesUseCase,
    private val keyStore: TemporaryKeyStore,
) : TranslationMemoryComponent,
    MviModel<TranslationMemoryComponent.ViewIntent, TranslationMemoryComponent.UiState, TranslationMemoryComponent.Effect> by mvi,
    ComponentContext by componentContext {
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

    override fun reduce(intent: TranslationMemoryComponent.ViewIntent) {
        when (intent) {
            TranslationMemoryComponent.ViewIntent.Clear -> clear()
            is TranslationMemoryComponent.ViewIntent.CopyTranslation -> copyTranslation(intent.index)
            is TranslationMemoryComponent.ViewIntent.Load -> load(
                key = intent.key,
                projectId = intent.projectId,
                languageId = intent.languageId,
            )
        }
    }

    private fun clear() {
        mvi.updateState { it.copy(units = emptyList()) }
    }

    private fun load(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            mvi.updateState { it.copy(isLoading = true) }
            val segment = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@launch
            val similarityThreshold = keyStore.get(KeyStoreKeys.SimilarityThreshold, 75) / 100f

            val newUnits = getSimilarities(
                segment = segment,
                projectId = projectId,
                languageId = languageId,
                threshold = similarityThreshold,
            )
            mvi.updateState {
                it.copy(
                    units = newUnits,
                    isLoading = false,
                )
            }
        }
    }

    private fun copyTranslation(index: Int) {
        viewModelScope.launch(dispatchers.io) {
            val unit = uiState.value.units.getOrNull(index) ?: return@launch
            mvi.emitEffect(TranslationMemoryComponent.Effect.Copy(unit.segment.text))
        }
    }
}
