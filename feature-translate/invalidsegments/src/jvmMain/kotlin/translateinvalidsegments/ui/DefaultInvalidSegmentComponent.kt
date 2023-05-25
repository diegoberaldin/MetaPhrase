package translateinvalidsegments.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.Constants
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
import repository.local.LanguageRepository
import repository.local.SegmentRepository
import kotlin.coroutines.CoroutineContext

class DefaultInvalidSegmentComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : InvalidSegmentComponent, ComponentContext by componentContext {

    private val references = MutableStateFlow<List<InvalidReference>>(emptyList())
    private val currentIndex = MutableStateFlow<Int?>(null)
    private lateinit var viewModelScope: CoroutineScope

    override val selectionEvents = MutableSharedFlow<String>()
    override var languageId: Int = 0
    override var projectId: Int = 0
    override var invalidKeys: List<String> = emptyList()
        set(value) {
            field = value
            loadReferences()
        }
    override lateinit var uiState: StateFlow<InvalidSegmentUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    references,
                    currentIndex,
                ) { references, currentIndex ->
                    InvalidSegmentUiState(
                        references = references,
                        currentIndex = currentIndex,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = InvalidSegmentUiState(),
                )
                if (invalidKeys.isNotEmpty()) {
                    loadReferences()
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private fun loadReferences() {
        viewModelScope.launch(dispatchers.io) {
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            references.value = invalidKeys.mapNotNull { key ->
                val target = segmentRepository.getByKey(key = key, languageId = languageId) ?: return@mapNotNull null
                val source =
                    segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: return@mapNotNull null

                val sourcePlaceholders = Constants.PlaceholderRegex.findAll(source.text).map { it.value }
                val targetPlaceholders = Constants.PlaceholderRegex.findAll(target.text).map { it.value }
                val exceeding = (targetPlaceholders - sourcePlaceholders.toSet()).toList()
                val missing = (sourcePlaceholders - targetPlaceholders.toSet()).toList()

                InvalidReference(key = key, extraPlaceholders = exceeding, missingPlaceholders = missing)
            }
        }
    }

    override fun setCurrentIndex(value: Int) {
        currentIndex.value = value
        val reference = references.value[value]
        viewModelScope.launch(dispatchers.io) {
            selectionEvents.emit(reference.key)
        }
    }
}
