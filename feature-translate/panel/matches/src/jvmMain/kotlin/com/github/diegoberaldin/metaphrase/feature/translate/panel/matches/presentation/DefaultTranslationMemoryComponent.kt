package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.GetSimilaritiesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultTranslationMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val segmentRepository: SegmentRepository,
    private var getSimilarities: GetSimilaritiesUseCase,
    private val keyStore: TemporaryKeyStore,
) : TranslationMemoryComponent, ComponentContext by componentContext {
    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(TranslationMemoryUiState())
    override val copyEvents = MutableSharedFlow<String>()

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

    override fun clear() {
        uiState.update { it.copy(units = emptyList()) }
    }

    override fun load(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            uiState.update { it.copy(isLoading = true) }
            val segment = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@launch
            val similarityThreshold = keyStore.get("similarity_threshold", 75) / 100f

            val newUnits = getSimilarities(
                segment = segment,
                projectId = projectId,
                languageId = languageId,
                threshold = similarityThreshold,
            )
            uiState.update {
                it.copy(
                    units = newUnits,
                    isLoading = false,
                )
            }
        }
    }

    override fun copyTranslation(index: Int) {
        viewModelScope.launch {
            val unit = uiState.value.units.getOrNull(index) ?: return@launch
            copyEvents.emit(unit.segment.text)
        }
    }
}
