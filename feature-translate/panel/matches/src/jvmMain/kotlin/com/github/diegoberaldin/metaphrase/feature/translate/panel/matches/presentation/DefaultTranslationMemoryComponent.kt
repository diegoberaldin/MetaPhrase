package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.GetSimilaritiesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    private val loading = MutableStateFlow(false)
    private val units = MutableStateFlow<List<TranslationUnit>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<TranslationMemoryUiState>
    override val copyEvents = MutableSharedFlow<String>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    loading,
                    units,
                ) { loading, units ->
                    TranslationMemoryUiState(
                        isLoading = loading,
                        units = units,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = TranslationMemoryUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun clear() {
        units.value = emptyList()
    }

    override fun load(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            loading.value = true
            val segment = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@launch
            val similarityThreshold = keyStore.get("similarity_threshold", 75) / 100f

            units.value = getSimilarities(
                segment = segment,
                projectId = projectId,
                languageId = languageId,
                threshold = similarityThreshold,
            )
            loading.value = false
        }
    }

    override fun copyTranslation(index: Int) {
        viewModelScope.launch {
            val unit = units.value.getOrNull(index) ?: return@launch
            copyEvents.emit(unit.segment.text)
        }
    }
}
