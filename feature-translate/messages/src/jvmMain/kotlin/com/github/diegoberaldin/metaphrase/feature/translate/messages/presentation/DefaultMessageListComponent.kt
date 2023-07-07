package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
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
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val mvi: DefaultMviModel<MessageListComponent.Intent, MessageListComponent.UiState, MessageListComponent.Effect> = DefaultMviModel(
        MessageListComponent.UiState(),
    ),
    private val projectRepository: ProjectRepository,
    private val segmentRepository: SegmentRepository,
    private val languageRepository: LanguageRepository,
    private val spellCheckRepository: SpellCheckRepository,
    private val notificationCenter: NotificationCenter,
    private val keyStore: TemporaryKeyStore,
) : MessageListComponent,
    MviModel<MessageListComponent.Intent, MessageListComponent.UiState, MessageListComponent.Effect> by mvi,
    ComponentContext by componentContext {

    companion object {
        private const val PAGE_SIZE = 15
    }

    private lateinit var viewModelScope: CoroutineScope
    private var saveJob: Job? = null
    private var spellcheckJob: Job? = null
    private var lastFilter = TranslationUnitTypeFilter.ALL
    private var lastSearch: String = ""
    private var projectId: Int = 0
    private var currentPage = -1

    override lateinit var editedSegment: StateFlow<SegmentModel?>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                editedSegment = uiState.map {
                    val idx = it.editingIndex ?: return@map null
                    uiState.value.units[idx].segment
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = null,
                )
            }
            doOnStart {
                notificationCenter.events.mapNotNull { it as? NotificationCenter.Event.ShowProgress }
                    .distinctUntilChanged().onEach { evt ->
                        mvi.updateState { it.copy(isShowingGlobalProgress = evt.visible) }
                    }.launchIn(viewModelScope)
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: MessageListComponent.Intent) {
        when (intent) {
            is MessageListComponent.Intent.AddToGlossarySource -> addToGlossarySource(
                lemma = intent.lemma,
                lang = intent.lang,
            )

            is MessageListComponent.Intent.ChangeSegmentText -> changeSegmentText(intent.text)
            MessageListComponent.Intent.ClearMessages -> clearMessages()
            MessageListComponent.Intent.CopyBase -> copyBase()
            MessageListComponent.Intent.DeleteSegment -> deleteSegment()
            MessageListComponent.Intent.EndEditing -> endEditing()
            is MessageListComponent.Intent.IgnoreWordInSpelling -> ignoreWordInSpelling(intent.word)
            MessageListComponent.Intent.LoadNextPage -> loadNextPage()
            is MessageListComponent.Intent.MarkAsTranslatable -> markAsTranslatable(
                value = intent.value,
                key = intent.key,
            )

            MessageListComponent.Intent.MoveToNext -> moveToNext()
            MessageListComponent.Intent.MoveToPrevious -> moveToPrevious()
            MessageListComponent.Intent.Refresh -> refresh()
            is MessageListComponent.Intent.ReloadMessages -> reloadMessages(
                language = intent.language,
                filter = intent.filter,
                projectId = intent.projectId,
            )

            is MessageListComponent.Intent.ScrollToMessage -> scrollToMessage(intent.key)
            is MessageListComponent.Intent.Search -> search(intent.text)
            is MessageListComponent.Intent.SetEditingEnabled -> setEditingEnabled(intent.value)
            is MessageListComponent.Intent.SetSegmentText -> setSegmentText(intent.text)
            is MessageListComponent.Intent.StartEditing -> startEditing(intent.index)
        }
    }

    private fun setEditingEnabled(value: Boolean) {
        mvi.updateState { it.copy(editingEnabled = value) }
    }

    private fun clearMessages() {
        mvi.updateState {
            it.copy(
                canFetchMore = false,
                units = emptyList(),
            )
        }
    }

    private fun search(text: String) {
        if (lastSearch == text) return
        lastSearch = text
        refresh()
    }

    private fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int) {
        if (!this::viewModelScope.isInitialized) return
        if (uiState.value.currentLanguage == language && lastFilter == filter && this.projectId == projectId) return

        lastFilter = filter
        this.projectId = projectId

        mvi.updateState {
            it.copy(
                currentLanguage = language,
                units = it.units.let { oldList ->
                    oldList.map { unit -> unit.copy(segment = unit.segment.copy(text = "")) }
                },
                updateTextSwitch = !it.updateTextSwitch,
            )
        }
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch(dispatchers.io) {
            innerReload()
        }
    }

    private suspend fun innerReload() {
        val language = uiState.value.currentLanguage ?: return
        notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
        mvi.updateState {
            it.copy(
                isLoading = true,
                canFetchMore = false,
                editingIndex = null,
                units = emptyList(),
            )
        }
        spellCheckRepository.setLanguage(language.code)
        currentPage = 0
        loadPage()
        notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        mvi.updateState { it.copy(isLoading = false) }
    }

    private fun loadNextPage() {
        if (uiState.value.isLoading) {
            return
        }
        mvi.updateState { it.copy(isLoading = true) }
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            currentPage++
            loadPage()
            mvi.updateState { it.copy(isLoading = false) }
        }
    }

    private suspend fun loadPage() {
        val language = uiState.value.currentLanguage ?: run {
            mvi.updateState { it.copy(canFetchMore = false) }
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
            baseLanguageId = baseLanguageId,
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
        mvi.updateState {
            it.copy(
                canFetchMore = unitsToAdd.isNotEmpty(),
                units = it.units + unitsToAdd,
            )
        }
    }

    private fun startEditing(index: Int) {
        if (!uiState.value.editingEnabled) return

        mvi.updateState { it.copy(spellingErrors = emptyList()) }
        val oldIndex = uiState.value.editingIndex
        mvi.updateState {
            it.copy(editingIndex = index)
        }
        if (oldIndex != null) {
            viewModelScope.launch(dispatchers.io) {
                val segment = uiState.value.units[oldIndex].segment
                segmentRepository.update(segment)
            }
        }

        val text = uiState.value.units[index].segment.text
        checkSpelling(text)
    }

    private fun endEditing() {
        if (!uiState.value.editingEnabled) return

        mvi.updateState { it.copy(editingIndex = null) }
        spellcheckJob?.cancel()
        spellcheckJob = null
        mvi.updateState { it.copy(spellingErrors = emptyList()) }
    }

    private fun setSegmentText(text: String) {
        val editingIndex = uiState.value.editingIndex ?: return
        val oldText = uiState.value.units[editingIndex].segment.text
        if (text == oldText) return

        projectRepository.setNeedsSaving(true)
        mvi.updateState {
            it.copy(
                units = it.units.let { oldList ->
                    oldList.mapIndexed { idx, unit ->
                        if (idx == editingIndex) {
                            unit.copy(segment = unit.segment.copy(text = text))
                        } else {
                            unit
                        }
                    }
                },
            )
        }

        saveCurrentSegmentDebounced(editingIndex)

        mvi.updateState { it.copy(spellingErrors = emptyList()) }
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
            mvi.updateState { it.copy(spellingErrors = results) }
        }
    }

    private fun changeSegmentText(text: String) {
        projectRepository.setNeedsSaving(true)
        setSegmentText(text)
        mvi.updateState { it.copy(updateTextSwitch = !it.updateTextSwitch) }
    }

    private fun saveCurrentSegmentDebounced(index: Int) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch(dispatchers.io) {
            delay(1000)
            val segment = uiState.value.units[index].segment.let {
                if (it.text.isBlank()) {
                    it.copy(text = "")
                } else {
                    it
                }
            }
            segmentRepository.update(segment)
        }
    }

    private fun moveToPrevious() {
        val index = uiState.value.editingIndex ?: return
        val newIndex = (index - 1).coerceAtLeast(0)
        viewModelScope.launch(dispatchers.io) {
            // ensure previous is visible too
            mvi.emitEffect(MessageListComponent.Effect.Selection(newIndex.coerceAtLeast(0)))
        }
    }

    private fun moveToNext() {
        val index = uiState.value.editingIndex ?: return
        val newIndex = (index + 1).coerceAtMost(uiState.value.units.lastIndex)
        viewModelScope.launch(dispatchers.io) {
            // ensure previous is visible too
            mvi.emitEffect(MessageListComponent.Effect.Selection(newIndex.coerceAtLeast(0)))
        }
    }

    private fun copyBase() {
        val index = uiState.value.editingIndex ?: return
        mvi.updateState { it.copy(editingIndex = null) }
        viewModelScope.launch(dispatchers.io) {
            delay(250) // to update textfield value
            mvi.updateState {
                it.copy(
                    units = it.units.let { oldList ->
                        oldList.mapIndexed { idx, unit ->
                            if (idx == index) {
                                val baseText = unit.original?.text ?: unit.segment.text
                                unit.copy(segment = unit.segment.copy(text = baseText))
                            } else {
                                unit
                            }
                        }
                    },
                )
            }
            saveCurrentSegmentDebounced(index)
            mvi.updateState { it.copy(editingIndex = index) }
        }
    }

    private fun deleteSegment() {
        val index = uiState.value.editingIndex ?: return
        projectRepository.setNeedsSaving(true)
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val toDelete = uiState.value.units[index].segment
            val key = toDelete.key
            segmentRepository.delete(toDelete)

            // remove segments with the same key in other languages too
            uiState.value.currentLanguage?.also { language ->
                val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }
                for (lang in otherLanguages) {
                    val existing = segmentRepository.getByKey(key = key, languageId = lang.id)
                    if (existing != null) {
                        segmentRepository.delete(existing)
                    }
                }
            }

            mvi.updateState {
                it.copy(
                    editingIndex = null,
                    units = it.units.let { oldList ->
                        oldList.filterIndexed { idx, _ -> idx != index }
                    },
                )
            }
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    private fun scrollToMessage(key: String) {
        suspend fun searchRec() {
            val index = uiState.value.units.indexOfFirst { it.segment.key == key }
            if (index >= 0) {
                notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
                mvi.emitEffect(MessageListComponent.Effect.Selection(index))
            } else if (uiState.value.canFetchMore) {
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
            mvi.updateState { it.copy(canFetchMore = true) }
            searchRec()
        }
    }

    private fun markAsTranslatable(value: Boolean, key: String) {
        val index = uiState.value.units.indexOfFirst { it.segment.key == key }
        val segment = uiState.value.units[index].segment
        val language = uiState.value.currentLanguage ?: return
        projectRepository.setNeedsSaving(true)
        viewModelScope.launch(dispatchers.io) {
            val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }
            val toUpdate = segment.copy(translatable = value)
            segmentRepository.update(toUpdate)

            mvi.updateState {
                it.copy(
                    units = it.units.let { oldList ->
                        oldList.mapIndexed { idx, it ->
                            if (idx != index) {
                                it
                            } else {
                                it.copy(segment = toUpdate)
                            }
                        }
                    },
                )
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

    private fun addToGlossarySource(lemma: String, lang: String) {
        viewModelScope.launch(dispatchers.io) {
            mvi.emitEffect(MessageListComponent.Effect.AddToGlossary(lemma = lemma, lang = lang))
        }
    }

    private fun ignoreWordInSpelling(word: String) {
        viewModelScope.launch(dispatchers.io) {
            spellCheckRepository.addUserDefineWord(word)
            val index = uiState.value.editingIndex
            if (index != null) {
                startEditing(index)
            }
        }
    }
}
