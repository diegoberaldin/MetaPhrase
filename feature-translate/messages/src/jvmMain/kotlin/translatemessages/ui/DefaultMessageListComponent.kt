package translatemessages.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import common.coroutines.CoroutineDispatcherProvider
import data.LanguageModel
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.local.LanguageRepository
import repository.local.SegmentRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultMessageListComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val segmentRepository: SegmentRepository,
    private val languageRepository: LanguageRepository,
) : MessageListComponent, ComponentContext by componentContext {

    private val units = MutableStateFlow<List<TranslationUnit>>(emptyList())
    private val editingIndex = MutableStateFlow<Int?>(null)
    private val isBaseLanguage = MutableStateFlow(false)
    private lateinit var viewModelScope: CoroutineScope
    private var saveJob: Job? = null
    private var lastLanguage: LanguageModel? = null
    private var lastFilter = TranslationUnitTypeFilter.ALL
    private var lastSearch: String = ""
    private var projectId: Int = 0

    override lateinit var uiState: StateFlow<MessageListUiState>
    override val selectionEvents = MutableSharedFlow<Int>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    units,
                    editingIndex,
                    isBaseLanguage,
                ) { units, editingIndex, isBaseLanguage ->
                    MessageListUiState(
                        units = units,
                        editingIndex = editingIndex,
                        isBaseLanguage = isBaseLanguage,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MessageListUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun search(text: String) {
        if (lastSearch == text) return
        lastSearch = text
        viewModelScope.launch(dispatchers.io) {
            innerReload()
        }
    }

    override fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        if (lastLanguage == language && lastFilter == filter && this.projectId == projectId) return

        lastLanguage = language
        lastFilter = filter
        this.projectId = projectId

        refresh()
    }

    override fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            innerReload()
        }
    }

    private suspend fun innerReload() {
        val language = lastLanguage ?: return
        val search = lastSearch.takeIf { it.isNotBlank() }
        editingIndex.value = null
        units.value = emptyList()

        val languageId = language.id
        isBaseLanguage.value = language.isBase
        val baseLanguageId = if (language.isBase) {
            languageId
        } else {
            languageRepository.getAll(projectId).firstOrNull { it.isBase }?.id ?: 0
        }

        val segments = segmentRepository.search(
            languageId = languageId,
            filter = lastFilter,
            search = search,
        )

        units.value = segments.map { segment ->
            if (isBaseLanguage.value) {
                TranslationUnit(
                    segment = segment,
                )
            } else {
                val key = segment.key
                val original = segmentRepository.getByKey(key = key, languageId = baseLanguageId)
                TranslationUnit(
                    segment = segment,
                    original = original,
                )
            }
        }
    }

    override fun startEditing(index: Int) {
        val oldIndex = editingIndex.value
        editingIndex.value = index
        if (oldIndex != null) {
            viewModelScope.launch(dispatchers.io) {
                val segment = units.value[oldIndex].segment
                segmentRepository.update(segment)
            }
        }
    }

    override fun endEditing() {
        editingIndex.value = null
    }

    override fun setSegmentText(text: String) {
        val editingIndex = editingIndex.value ?: return

        units.getAndUpdate { oldList ->
            oldList.mapIndexed { idx, unit ->
                if (idx == editingIndex) {
                    unit.copy(segment = unit.segment.copy(text = text))
                } else {
                    unit
                }
            }
        }

        saveCurrentSegmentDebounced(editingIndex)
    }

    private fun saveCurrentSegmentDebounced(index: Int) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch(dispatchers.io) {
            delay(1000)
            val segment = units.value[index].segment.let {
                if (it.text.isBlank()) {
                    it.copy(text = "")
                } else {
                    it
                }
            }
            segmentRepository.update(segment)
        }
    }

    override fun moveToPrevious() {
        val index = editingIndex.value ?: return
        editingIndex.value = (index - 1).coerceAtLeast(0)
    }

    override fun moveToNext() {
        val index = editingIndex.value ?: return
        editingIndex.value = (index + 1).coerceAtMost(units.value.lastIndex)
    }

    override fun copyBase() {
        val index = editingIndex.value ?: return
        editingIndex.value = null
        viewModelScope.launch(dispatchers.io) {
            delay(250) // to update textfield value
            units.getAndUpdate { oldList ->
                oldList.mapIndexed { idx, unit ->
                    if (idx == index) {
                        val baseText = unit.original?.text ?: unit.segment.text
                        unit.copy(segment = unit.segment.copy(text = baseText))
                    } else {
                        unit
                    }
                }
            }
            saveCurrentSegmentDebounced(index)
            editingIndex.value = index
        }
    }

    override fun deleteSegment() {
        val index = editingIndex.value ?: return
        viewModelScope.launch(dispatchers.io) {
            val toDelete = units.value[index].segment
            val key = toDelete.key
            segmentRepository.delete(toDelete)

            // remove segments with the same key in other languages too
            lastLanguage?.also { language ->
                val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }
                for (lang in otherLanguages) {
                    val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                    if (existing != null) {
                        segmentRepository.delete(existing)
                    }
                }
            }

            editingIndex.value = null
            units.getAndUpdate { oldList ->
                oldList.filterIndexed { idx, _ -> idx != index }
            }
        }
    }

    override fun scrollToMessage(key: String) {
        viewModelScope.launch {
            val index = units.value.indexOfFirst { it.segment.key == key }
            if (index >= 0) {
                selectionEvents.emit(index)
            }
        }
    }
}
