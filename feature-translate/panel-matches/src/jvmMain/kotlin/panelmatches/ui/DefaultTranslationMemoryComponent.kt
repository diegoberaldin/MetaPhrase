package panelmatches.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import common.keystore.TemporaryKeyStore
import data.TranslationUnit
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
import repository.repo.SegmentRepository
import translationmemory.repo.TranslationMemoryRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultTranslationMemoryComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val segmentRepository: SegmentRepository,
    private var translationMemoryRepository: TranslationMemoryRepository,
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

    override fun loadSimilarities(key: String, projectId: Int, languageId: Int) {
        viewModelScope.launch(dispatchers.io) {
            loading.value = true
            val segment = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@launch
            val similarityThreshold = keyStore.get("similarity_threshold", 75) / 100f

            units.value = translationMemoryRepository.getSimilarities(
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
