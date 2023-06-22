package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

internal class DefaultMessageListComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val segmentRepository: SegmentRepository,
    private val languageRepository: LanguageRepository,
    private val spellCheckRepository: SpellCheckRepository,
    private val notificationCenter: NotificationCenter,
    private val keyStore: TemporaryKeyStore,
) : MessageListComponent, ComponentContext by componentContext {

    companion object {
        private const val PAGE_SIZE = 15
    }

    private val units = MutableStateFlow<List<TranslationUnit>>(emptyList())
    private val editingIndex = MutableStateFlow<Int?>(null)
    private val currentLanguage = MutableStateFlow<LanguageModel?>(null)
    private val editingEnabled = MutableStateFlow(true)
    private val updateTextSwitch = MutableStateFlow(false)
    private val isLoading = MutableStateFlow(false)
    private val canFetchMore = MutableStateFlow(true)
    private lateinit var viewModelScope: CoroutineScope
    private var saveJob: Job? = null
    private var spellcheckJob: Job? = null
    private var lastFilter = TranslationUnitTypeFilter.ALL
    private var lastSearch: String = ""
    private var projectId: Int = 0
    private var currentPage = -1

    override lateinit var uiState: StateFlow<MessageListUiState>
    override lateinit var paginationState: StateFlow<MessageLisPaginationState>
    override val selectionEvents = MutableSharedFlow<Int>()
    override lateinit var editedSegment: StateFlow<SegmentModel?>
    override val spellingErrors = MutableStateFlow<List<SpellCheckCorrection>>(emptyList())
    override val addToGlossaryEvents = MutableSharedFlow<AddToGlossaryEvent>()
    override val isShowingProgress = MutableStateFlow(false)

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    units,
                    editingIndex,
                    currentLanguage,
                    editingEnabled,
                    updateTextSwitch,
                ) { units, editingIndex, currentLanguage, editingEnabled, updateTextSwitch ->
                    MessageListUiState(
                        units = units,
                        editingIndex = editingIndex,
                        currentLanguage = currentLanguage,
                        editingEnabled = editingEnabled,
                        updateTextSwitch = updateTextSwitch,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MessageListUiState(),
                )
                editedSegment = editingIndex.map {
                    if (it != null) {
                        units.value[it].segment
                    } else {
                        null
                    }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
                paginationState = combine(
                    isLoading,
                    canFetchMore,
                ) { isLoading, canFetchMore ->
                    MessageLisPaginationState(
                        isLoading = isLoading,
                        canFetchMore = canFetchMore,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MessageLisPaginationState(),
                )
            }
            doOnStart {
                notificationCenter.events.mapNotNull { it as? NotificationCenter.Event.ShowProgress }
                    .distinctUntilChanged().onEach {
                        isShowingProgress.value = it.visible
                    }.launchIn(viewModelScope)
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setEditingEnabled(value: Boolean) {
        editingEnabled.value = value
    }

    override fun clearMessages() {
        canFetchMore.value = false
        units.value = emptyList()
    }

    override fun search(text: String) {
        if (lastSearch == text) return
        lastSearch = text
        refresh()
    }

    override fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        if (currentLanguage.value == language && lastFilter == filter && this.projectId == projectId) return

        currentLanguage.value = language
        lastFilter = filter
        this.projectId = projectId

        units.getAndUpdate { oldList ->
            oldList.map { it.copy(segment = it.segment.copy(text = "")) }
        }
        updateTextSwitch.getAndUpdate { !it }
        refresh()
    }

    override fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            innerReload()
        }
    }

    private suspend fun innerReload() {
        val language = currentLanguage.value ?: return
        editingIndex.value = null
        notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
        isLoading.value = true
        canFetchMore.value = false

        currentLanguage.value = language
        spellCheckRepository.setLanguage(language.code)

        units.value = emptyList()
        currentPage = 0
        loadPage()
        notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        isLoading.value = false
    }

    override fun loadNextPage() {
        if (isLoading.value) {
            return
        }
        isLoading.value = true
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            currentPage++
            loadPage()
            isLoading.value = false
        }
    }

    private suspend fun loadPage() {
        val language = currentLanguage.value ?: run {
            canFetchMore.value = false
            return
        }
        val baseLanguageId = if (language.isBase) {
            language.id
        } else {
            languageRepository.getBase(projectId)?.id ?: 0
        }
        val search = lastSearch.takeIf { it.isNotBlank() }
        val segments = segmentRepository.search(
            languageId = language.id,
            filter = lastFilter,
            search = search,
            skip = currentPage * PAGE_SIZE,
            limit = PAGE_SIZE,
        )
        val unitsToAdd = segments.map { segment ->
            if (language.isBase) {
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
        canFetchMore.value = unitsToAdd.isNotEmpty()
        units.getAndUpdate {
            it + unitsToAdd
        }
    }

    override fun startEditing(index: Int) {
        if (!editingEnabled.value) return

        spellingErrors.value = emptyList()
        val oldIndex = editingIndex.value
        editingIndex.value = index
        if (oldIndex != null) {
            viewModelScope.launch(dispatchers.io) {
                val segment = units.value[oldIndex].segment
                segmentRepository.update(segment)
            }
        }

        val text = units.value[index].segment.text
        checkSpelling(text)
    }

    override fun endEditing() {
        if (!editingEnabled.value) return

        editingIndex.value = null
        spellcheckJob?.cancel()
        spellcheckJob = null
        spellingErrors.value = emptyList()
    }

    override fun setSegmentText(text: String) {
        val editingIndex = editingIndex.value ?: return
        val oldText = units.value[editingIndex].segment.text
        if (text == oldText) return

        projectRepository.setNeedsSaving(true)
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

        spellingErrors.value = emptyList()
        checkSpelling(text)
    }

    private fun checkSpelling(text: String) {
        spellcheckJob?.cancel()
        val isEnabled = runBlocking {
            keyStore.get(KeyStoreKeys.SpellcheckEnabled, false)
        }
        if (!isEnabled) {
            return
        }

        spellcheckJob = viewModelScope.launch(dispatchers.io) {
            delay(1000)
            val results = spellCheckRepository.check(message = text)
            spellingErrors.value = results
        }
    }

    override fun changeSegmentText(text: String) {
        projectRepository.setNeedsSaving(true)
        setSegmentText(text)
        updateTextSwitch.getAndUpdate { !it }
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
        val newIndex = (index - 1).coerceAtLeast(0)
        viewModelScope.launch {
            // ensure previous is visible too
            selectionEvents.emit((newIndex).coerceAtLeast(0))
        }
    }

    override fun moveToNext() {
        val index = editingIndex.value ?: return
        val newIndex = (index + 1).coerceAtMost(units.value.lastIndex)
        viewModelScope.launch {
            // ensure previous is visible too
            selectionEvents.emit((newIndex).coerceAtLeast(0))
        }
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
        projectRepository.setNeedsSaving(true)
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val toDelete = units.value[index].segment
            val key = toDelete.key
            segmentRepository.delete(toDelete)

            // remove segments with the same key in other languages too
            currentLanguage.value?.also { language ->
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
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun scrollToMessage(key: String) {
        suspend fun searchRec() {
            val index = units.value.indexOfFirst { it.segment.key == key }
            if (index >= 0) {
                notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
                selectionEvents.emit(index)
            } else if (this.canFetchMore.value) {
                notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
                currentPage++
                loadPage()
                delay(250)
                searchRec()
            } else {
                notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
            }
        }

        viewModelScope.launch(dispatchers.io) {
            currentPage = 0
            canFetchMore.value = true
            searchRec()
        }
    }

    override fun markAsTranslatable(value: Boolean, key: String) {
        val index = units.value.indexOfFirst { it.segment.key == key }
        val segment = units.value[index].segment
        val language = currentLanguage.value ?: return
        projectRepository.setNeedsSaving(true)
        viewModelScope.launch(dispatchers.io) {
            val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }
            val toUpdate = segment.copy(translatable = value)
            segmentRepository.update(toUpdate)

            units.getAndUpdate { oldList ->
                oldList.mapIndexed { idx, it ->
                    if (idx != index) {
                        it
                    } else {
                        it.copy(segment = toUpdate)
                    }
                }
            }

            if (!value) {
                // deletes all the segments in the non-base language
                for (lang in otherLanguages) {
                    val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                    if (existing != null) {
                        segmentRepository.delete(existing)
                    }
                }
            } else {
                for (lang in otherLanguages) {
                    val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                    if (existing != null) {
                        segmentRepository.update(existing.copy(translatable = true))
                    } else {
                        segmentRepository.create(SegmentModel(key = key), languageId = lang.id)
                    }
                }
            }
        }
    }

    override fun addToGlossarySource(lemma: String, lang: String) {
        viewModelScope.launch(dispatchers.io) {
            addToGlossaryEvents.emit(AddToGlossaryEvent(lemma = lemma, lang = lang))
        }
    }

    override fun ignoreWordInSpelling(word: String) {
        viewModelScope.launch(dispatchers.io) {
            spellCheckRepository.addUserDefineWord(word)
            val index = editingIndex.value
            if (index != null) {
                startEditing(index)
            }
        }
    }
}
