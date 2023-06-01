package translate.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import common.coroutines.CoroutineDispatcherProvider
import common.notification.NotificationCenter
import common.utils.asFlow
import common.utils.getByInjection
import data.LanguageModel
import data.ProjectModel
import data.ResourceFileType
import data.SegmentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repository.local.LanguageRepository
import repository.local.ProjectRepository
import repository.local.SegmentRepository
import repository.usecase.ExportAndroidResourcesUseCase
import repository.usecase.ExportIosResourcesUseCase
import repository.usecase.ExportTmxUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ImportTmxUseCase
import repository.usecase.ParseAndroidResourcesUseCase
import repository.usecase.ParseIosResourcesUseCase
import repository.usecase.ValidatePlaceholdersUseCase
import translate.ui.TranslateComponent.DialogConfig
import translate.ui.TranslateComponent.MessageListConfig
import translate.ui.TranslateComponent.PanelConfig
import translate.ui.TranslateComponent.ToolbarConfig
import translatebrowsememory.ui.BrowseMemoryComponent
import translateinvalidsegments.ui.InvalidSegmentComponent
import translatemessages.ui.MessageListComponent
import translatenewsegment.ui.NewSegmentComponent
import translatetoolbar.ui.TranslateToolbarComponent
import translationtranslationmemory.ui.TranslationMemoryComponent
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultTranslateComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val parseAndroidResources: ParseAndroidResourcesUseCase,
    private val parseIosResources: ParseIosResourcesUseCase,
    private val importSegments: ImportSegmentsUseCase,
    private val exportAndroidResources: ExportAndroidResourcesUseCase,
    private val exportIosResources: ExportIosResourcesUseCase,
    private val validatePlaceholders: ValidatePlaceholdersUseCase,
    private val notificationCenter: NotificationCenter,
    private val exportToTmx: ExportTmxUseCase,
    private val importFromTmx: ImportTmxUseCase,
) : TranslateComponent, ComponentContext by componentContext {

    private val project = MutableStateFlow<ProjectModel?>(null)
    private val unitCount = MutableStateFlow(0)

    private val toolbarNavigation = SlotNavigation<ToolbarConfig>()
    private val messageListNavigation = SlotNavigation<MessageListConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val panelNavigation = SlotNavigation<PanelConfig>()

    private lateinit var viewModelScope: CoroutineScope

    override var projectId: Int = 0
        set(value) {
            field = value
            loadProject()
        }

    override lateinit var uiState: StateFlow<TranslateUiState>
    override val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>> = childSlot(
        source = toolbarNavigation,
        key = KEY_TOOLBAR_SLOT,
        childFactory = { _, context ->
            getByInjection<TranslateToolbarComponent>(context, coroutineContext)
        },
    )
    override val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>> = childSlot(
        source = messageListNavigation,
        key = KEY_MESSAGE_LIST_SLOT,
        childFactory = { _, context ->
            getByInjection<MessageListComponent>(context, coroutineContext)
        },
    )
    override val panel: Value<ChildSlot<PanelConfig, *>> = childSlot(
        source = panelNavigation,
        key = KEY_PANEL_SLOT,
        childFactory = ::createPanelComponent,
    )
    override val dialog: Value<ChildSlot<DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = KEY_DIALOG_SLOT,
        childFactory = ::createDialogComponent,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val isEditing: StateFlow<Boolean>
        get() = messageList.asFlow<MessageListComponent>().filterNotNull().flatMapLatest { it.editedSegment }
            .map { it != null }
            .stateIn(viewModelScope, initialValue = false, started = SharingStarted.WhileSubscribed(5000))

    override val currentLanguage: StateFlow<LanguageModel?>
        get() {
            return toolbar.asFlow<TranslateToolbarComponent>().filterNotNull().flatMapLatest { it.currentLanguage }
                .stateIn(viewModelScope, initialValue = null, started = SharingStarted.WhileSubscribed(5000))
        }

    private suspend fun getCurrentLanguage(): LanguageModel? =
        toolbar.asFlow<TranslateToolbarComponent>().firstOrNull()?.currentLanguage?.value

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                // done here to reinitialize flow due to sharing started policy
                uiState = combine(
                    project,
                    unitCount,
                ) { project, unitCount ->
                    TranslateUiState(
                        project = project,
                        unitCount = unitCount,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = TranslateUiState(),
                )

                viewModelScope.launch {
                    configureToolbar()
                    configureMessageList()
                    configurePanel()
                }

                toolbarNavigation.activate(ToolbarConfig)
                messageListNavigation.activate(MessageListConfig)
                panelNavigation.activate(PanelConfig.None)
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    updateUnitCount()

                    projectRepository.observeById(projectId).onEach {
                        if (it.name != project.value?.name) {
                            project.value = it
                        }
                    }.launchIn(this)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private suspend fun CoroutineScope.configureMessageList() {
        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull() ?: return
        val toolbarComponent = toolbar.asFlow<TranslateToolbarComponent>().firstOrNull() ?: return
        messageListComponent.editedSegment.onEach { segment ->
            val isEditing = segment != null
            toolbarComponent.setEditing(isEditing)
            if (!isEditing) {
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.clear()
            }
        }.launchIn(this)
        dialog.asFlow<NewSegmentComponent>().filterNotNull().onEach {
            it.done.onEach { newSegment ->
                withContext(dispatchers.main) {
                    dialogNavigation.activate(DialogConfig.None)
                }
                if (newSegment != null) {
                    updateUnitCount()
                    messageListComponent.refresh()
                }
            }.launchIn(this)
        }.launchIn(this)
        messageListComponent.editedSegment.filterNotNull().onEach { segment ->
            val key = segment.key
            val child = panel.asFlow<TranslationMemoryComponent>().firstOrNull()
            child?.loadSimilarities(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
        }.launchIn(this)
    }

    private suspend fun CoroutineScope.configureToolbar() {
        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull() ?: return
        val toolbarComponent = toolbar.asFlow<TranslateToolbarComponent>().firstOrNull() ?: return
        toolbarComponent.projectId = projectId
        toolbarComponent.uiState.mapLatest { it.currentLanguage to it.currentTypeFilter }.distinctUntilChanged()
            .onEach { (language, filter) ->
                if (language == null) return@onEach
                messageListComponent.setEditingEnabled(false)
                messageListComponent.reloadMessages(
                    language = language,
                    filter = filter,
                    projectId = projectId,
                )
                messageListComponent.setEditingEnabled(true)
                // resets the current validation
                panel.asFlow<InvalidSegmentComponent>().firstOrNull()?.clear()
                // resets the TM
                panel.asFlow<BrowseMemoryComponent>().firstOrNull()?.setLanguages(
                    source = languageRepository.getBase(projectId),
                    target = getCurrentLanguage(),
                )
            }.launchIn(this)
        toolbarComponent.events.onEach { evt ->
            when (evt) {
                TranslateToolbarComponent.Events.MoveToPrevious -> {
                    moveToPrevious()
                }

                TranslateToolbarComponent.Events.MoveToNext -> {
                    moveToNext()
                }

                is TranslateToolbarComponent.Events.Search -> {
                    val searchText = evt.text
                    messageListComponent.search(searchText)
                }

                TranslateToolbarComponent.Events.CopyBase -> {
                    copyBase()
                }

                TranslateToolbarComponent.Events.AddUnit -> {
                    addSegment()
                }

                TranslateToolbarComponent.Events.RemoveUnit -> {
                    deleteSegment()
                }

                TranslateToolbarComponent.Events.ValidateUnits -> {
                    startValidation()
                }
            }
        }.launchIn(this)
    }

    private suspend fun CoroutineScope.configurePanel() {
        panel.asFlow<Any>(timeout = Duration.INFINITE).onEach { child ->
            when (child) {
                is TranslationMemoryComponent -> {
                    child.copyEvents.onEach { segmentId ->
                        val segment = segmentRepository.getById(segmentId)
                        if (segment != null) {
                            val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                            messageListComponent?.changeSegmentText(segment.text)
                        }
                    }.launchIn(this)
                }

                is InvalidSegmentComponent -> {
                    child.selectionEvents.onEach { key ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.scrollToMessage(key)
                    }.launchIn(this)
                }

                is BrowseMemoryComponent -> {
                    child.setLanguages(
                        source = languageRepository.getBase(projectId),
                        target = getCurrentLanguage(),
                    )
                }
            }
        }.launchIn(this)
    }

    private fun createDialogComponent(config: DialogConfig, context: ComponentContext): Any {
        return when (config) {
            DialogConfig.NewSegment -> getByInjection<NewSegmentComponent>(context, coroutineContext)
            else -> Unit
        }
    }

    private fun createPanelComponent(config: PanelConfig, context: ComponentContext): Any {
        return when (config) {
            PanelConfig.Matches -> getByInjection<TranslationMemoryComponent>(context, coroutineContext)
            PanelConfig.Validation -> getByInjection<InvalidSegmentComponent>(context, coroutineContext)
            PanelConfig.MemoryContent -> getByInjection<BrowseMemoryComponent>(context, coroutineContext)
            else -> Unit
        }
    }

    private fun loadProject() {
        if (!this::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            val proj = projectRepository.getById(projectId)
            project.value = proj
            updateUnitCount()
        }
    }

    private suspend fun updateUnitCount() {
        val baseLanguage = languageRepository.getBase(projectId)
        if (baseLanguage != null) {
            val baseSegments = segmentRepository.getAll(baseLanguage.id)
            unitCount.value = baseSegments.size
        }
    }

    override fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.None)
        }
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
            messageListComponent?.setEditingEnabled(false)
            messageListComponent?.clearMessages()
            when (type) {
                ResourceFileType.ANDROID_XML -> {
                    val segments = parseAndroidResources(path = path)
                    importSegments(
                        segments = segments,
                        language = language,
                        projectId = projectId,
                    )
                }

                ResourceFileType.IOS_STRINGS -> {
                    val segments = parseIosResources(path = path)
                    importSegments(
                        segments = segments,
                        language = language,
                        projectId = projectId,
                    )
                }

                else -> Unit
            }
            delay(100)
            updateUnitCount()
            messageListComponent?.refresh()
            messageListComponent?.setEditingEnabled(true)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val segments = segmentRepository.getAll(language.id)
            when (type) {
                ResourceFileType.ANDROID_XML -> {
                    exportAndroidResources(
                        segments = segments,
                        path = path,
                    )
                }

                ResourceFileType.IOS_STRINGS -> {
                    // for iOS it is needed to copy the base version of all untranslatable segments
                    val baseLanguage = languageRepository.getBase(projectId)
                    val toExport = if (baseLanguage != null) {
                        val untranslatable = segmentRepository.getUntranslatable(baseLanguage.id)
                        segments + untranslatable
                    } else {
                        segments
                    }
                    exportIosResources(
                        segments = toExport,
                        path = path,
                    )
                }

                else -> Unit
            }
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.NewSegment)
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.deleteSegment()
        }
    }

    private fun startValidation() {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val segmentPairs = segmentRepository.getAll(language.id).map {
                val key = it.key
                val original = segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: SegmentModel()
                it to original
            }
            val result = validatePlaceholders(segmentPairs)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))

            withContext(dispatchers.main) {
                panelNavigation.activate(PanelConfig.Validation)
            }
            val child = panel.asFlow<InvalidSegmentComponent>().firstOrNull()
            when (result) {
                ValidatePlaceholdersUseCase.Output.Valid -> {
                    child?.load(projectId, language.id, invalidKeys = emptyList())
                }

                is ValidatePlaceholdersUseCase.Output.Invalid -> {
                    child?.load(projectId, language.id, invalidKeys = result.keys)
                }
            }
        }
    }

    override fun togglePanel(config: PanelConfig) {
        when (panel.value.child?.configuration) {
            config -> panelNavigation.activate(PanelConfig.None)
            else -> panelNavigation.activate(config)
        }
    }

    override fun tryLoadSimilarities() {
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            val currentKey = messageList.asFlow<MessageListComponent>().firstOrNull()?.editedSegment?.value?.key
            if (currentKey != null) {
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.loadSimilarities(
                    key = currentKey,
                    languageId = getCurrentLanguage()?.id ?: 0,
                    projectId = projectId,
                )
            }
        }
    }

    override fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            exportToTmx(path = path, projectId = projectId)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun importTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            importFromTmx(path = path)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    companion object {
        const val KEY_DIALOG_SLOT = "DialogSlot"
        const val KEY_TOOLBAR_SLOT = "ToolbarSlot"
        const val KEY_MESSAGE_LIST_SLOT = "MessageListSlot"
        const val KEY_PANEL_SLOT = "PanelSlot"
    }
}
