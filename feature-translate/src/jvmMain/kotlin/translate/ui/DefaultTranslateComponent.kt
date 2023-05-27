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
import common.utils.getByInjection
import common.utils.observeChildSlot
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
import kotlinx.coroutines.flow.first
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
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ParseAndroidResourcesUseCase
import repository.usecase.ParseIosResourcesUseCase
import repository.usecase.ValidatePlaceholdersUseCase
import translate.ui.TranslateComponent.*
import translateinvalidsegments.ui.InvalidSegmentComponent
import translatemessages.ui.MessageListComponent
import translatenewsegment.ui.NewSegmentComponent
import translatetoolbar.ui.TranslateToolbarComponent
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultTranslateComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
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
) : TranslateComponent, ComponentContext by componentContext {

    private val project = MutableStateFlow<ProjectModel?>(null)
    private val unitCount = MutableStateFlow(0)

    private val toolbarNavigation = SlotNavigation<ToolbarConfig>()
    private val messageListNavigation = SlotNavigation<MessageListConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()

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
    override val dialog: Value<ChildSlot<DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = KEY_DIALOG_SLOT,
        childFactory = { config, context ->
            when (config) {
                DialogConfig.NewSegment -> getByInjection<NewSegmentComponent>(context, coroutineContext)
                is DialogConfig.PlaceholderInvalid -> getByInjection<InvalidSegmentComponent>(context, coroutineContext)
                else -> Unit
            }
        },
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val isEditing
        get() = observeChildSlot<MessageListComponent>(messageList).flatMapLatest { it.uiState }.distinctUntilChanged()
            .map { it.editingIndex != null }
            .stateIn(viewModelScope, initialValue = false, started = SharingStarted.WhileSubscribed(5000))

    override val currentLanguage: StateFlow<LanguageModel?>
        get() = observeChildSlot<TranslateToolbarComponent>(toolbar).flatMapLatest { it.uiState }.distinctUntilChanged()
            .map { it.currentLanguage }
            .stateIn(viewModelScope, initialValue = null, started = SharingStarted.WhileSubscribed(5000))

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
                toolbarNavigation.activate(ToolbarConfig)
                messageListNavigation.activate(MessageListConfig)

                viewModelScope.launch {
                    val toolbarComponent = observeChildSlot<TranslateToolbarComponent>(toolbar).first()
                    val messageListComponent = observeChildSlot<MessageListComponent>(messageList).first()
                    toolbarComponent.projectId = projectId
                    toolbarComponent.uiState.mapLatest { it.currentLanguage to it.currentTypeFilter }
                        .distinctUntilChanged().onEach { (language, filter) ->
                            if (language == null) return@onEach
                            messageListComponent.setEditingEnabled(false)
                            messageListComponent.reloadMessages(
                                language = language,
                                filter = filter,
                                projectId = projectId,
                            )
                            messageListComponent.setEditingEnabled(true)
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
                    messageListComponent.uiState.map { it.editingIndex }.distinctUntilChanged().onEach {
                        toolbarComponent.setEditing(it != null)
                    }.launchIn(this)
                    observeChildSlot<NewSegmentComponent>(dialog).onEach {
                        it.done.onEach { newSegment ->
                            withContext(dispatchers.main) {
                                dialogNavigation.activate(DialogConfig.None)
                            }
                            if (newSegment != null) {
                                updateUnitCount()
                                observeChildSlot<MessageListComponent>(messageList).firstOrNull()?.refresh()
                            }
                        }.launchIn(this)
                    }.launchIn(this)
                    observeChildSlot<InvalidSegmentComponent>(dialog).onEach {
                        it.selectionEvents.onEach { key ->
                            messageListComponent.scrollToMessage(key)
                        }.launchIn(this)
                    }.launchIn(this)
                }
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
            val toolbarState = observeChildSlot<TranslateToolbarComponent>(toolbar).first().uiState.value
            val language = toolbarState.currentLanguage ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val messageListComponent = observeChildSlot<MessageListComponent>(messageList).firstOrNull()
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
            val language = observeChildSlot<TranslateToolbarComponent>(toolbar).first().uiState.value.currentLanguage
                ?: return@launch
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
            observeChildSlot<MessageListComponent>(messageList).first().moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<MessageListComponent>(messageList).first().moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<MessageListComponent>(messageList).first().endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<MessageListComponent>(messageList).first().copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.NewSegment)
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<MessageListComponent>(messageList).first().deleteSegment()
        }
    }

    private fun startValidation() {
        val projectId = project.value?.id ?: return
        viewModelScope.launch(dispatchers.io) {
            val toolbarState = observeChildSlot<TranslateToolbarComponent>(toolbar).first().uiState.value
            val language = toolbarState.currentLanguage ?: return@launch
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            val segmentPairs = segmentRepository.getAll(language.id).map {
                val key = it.key
                val original = segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: SegmentModel()
                it to original
            }
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val result = validatePlaceholders(segmentPairs)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
            when (result) {
                ValidatePlaceholdersUseCase.Output.Valid -> {
                    withContext(dispatchers.main) {
                        dialogNavigation.activate(DialogConfig.PlaceholderValid)
                    }
                }

                is ValidatePlaceholdersUseCase.Output.Invalid -> {
                    withContext(dispatchers.main) {
                        val invalidKeys = result.keys
                        dialogNavigation.activate(DialogConfig.PlaceholderInvalid(invalidKeys))
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_DIALOG_SLOT = "DialogSlot"
        const val KEY_TOOLBAR_SLOT = "ToolbarSlot"
        const val KEY_MESSAGE_LIST_SLOT = "MessageListSlot"
    }
}
