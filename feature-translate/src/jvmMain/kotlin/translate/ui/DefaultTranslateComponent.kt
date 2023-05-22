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
import common.utils.observeChildSlot
import data.ProjectModel
import data.ResourceFileType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.local.LanguageRepository
import repository.local.ProjectRepository
import repository.local.SegmentRepository
import repository.usecase.ExportAndroidResourcesUseCase
import repository.usecase.ExportIosResourcesUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ParseAndroidResourcesUseCase
import repository.usecase.ParseIosResourcesUseCase
import translate.ui.TranslateComponent.MessageListConfig
import translate.ui.TranslateComponent.ToolbarConfig
import translate.ui.messagelist.MessageListComponent
import translate.ui.toolbar.TranslateToolbarComponent
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
) : TranslateComponent, ComponentContext by componentContext {

    private val project = MutableStateFlow<ProjectModel?>(null)
    private val unitCount = MutableStateFlow(0)

    private val toolbarNavigation = SlotNavigation<ToolbarConfig>()
    private val messageListNavigation = SlotNavigation<MessageListConfig>()

    private lateinit var viewModelScope: CoroutineScope
    private lateinit var _uiState: StateFlow<TranslateUiState>

    override var projectId: Int = 0
        set(value) {
            field = value
            loadProject()
        }

    override val uiState: StateFlow<TranslateUiState>
        get() = _uiState

    override val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>> =
        childSlot(
            source = toolbarNavigation,
            key = KEY_TOOLBAR_SLOT,
            childFactory = { _, context ->
                TranslateToolbarComponent.Factory.create(
                    componentContext = context,
                    coroutineContext = coroutineContext,
                )
            },
        )

    override val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>> =
        childSlot(
            source = messageListNavigation,
            key = KEY_MESSAGE_LIST_SLOT,
            childFactory = { _, context ->
                MessageListComponent.Factory.create(
                    componentContext = context,
                    coroutineContext = coroutineContext,
                )
            },
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val isEditing
        get() = observeChildSlot<MessageListComponent>(messageList).flatMapLatest { it.uiState }
            .distinctUntilChanged()
            .map { it.editingIndex != null }
            .stateIn(viewModelScope, initialValue = false, started = SharingStarted.WhileSubscribed(5000))

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                // done here to reinitialize flow due to sharing started policy
                _uiState = combine(
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
                        .distinctUntilChanged()
                        .onEach { (language, filter) ->
                            if (language == null) return@onEach
                            messageListComponent.reloadMessages(
                                language = language,
                                filter = filter,
                                projectId = projectId
                            )
                        }.launchIn(this)
                    toolbarComponent.events.onEach { evt ->
                        when (evt) {
                            // TODO: process events
                            TranslateToolbarComponent.Events.MoveToPrevious -> {
                                moveToPrevious()
                            }

                            TranslateToolbarComponent.Events.MoveToNext -> {
                                moveToNext()
                            }

                            else -> Unit
                        }
                    }.launchIn(this)
                    messageListComponent.uiState.map { it.editingIndex }.distinctUntilChanged().onEach {
                        toolbarComponent.setEditing(it != null)
                    }.launchIn(viewModelScope)
                }
            }
            doOnStart {
                if (project.value == null) {
                    loadProject()
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
        val baseLanguage = languageRepository.getAll(projectId).firstOrNull { it.isBase }
        if (baseLanguage != null) {
            val baseSegments = segmentRepository.getAll(baseLanguage.id)
            unitCount.value = baseSegments.size
        }
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val toolbarState = observeChildSlot<TranslateToolbarComponent>(toolbar).first().uiState.value
            val language = toolbarState.currentLanguage ?: return@launch
            val filter = toolbarState.currentTypeFilter
            when (type) {
                ResourceFileType.ANDROID_XML -> {
                    val segments = parseAndroidResources(path = path)
                    importSegments(
                        segments = segments,
                        language = language,
                        projectId = projectId,
                    )
                    updateUnitCount()
                    val messageListComponent = observeChildSlot<MessageListComponent>(messageList).first()
                    messageListComponent.reloadMessages(language = language, filter = filter, projectId = projectId)
                }

                ResourceFileType.IOS_STRINGS -> {
                    val segments = parseIosResources(path = path)
                    importSegments(
                        segments = segments,
                        language = language,
                        projectId = projectId,
                    )
                    updateUnitCount()
                    val messageListComponent = observeChildSlot<MessageListComponent>(messageList).first()
                    messageListComponent.reloadMessages(language = language, filter = filter, projectId = projectId)
                }

                else -> Unit
            }
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = observeChildSlot<TranslateToolbarComponent>(toolbar).first().uiState.value.currentLanguage
                ?: return@launch
            val segments = segmentRepository.getAll(language.id)
            when (type) {
                ResourceFileType.ANDROID_XML -> {
                    exportAndroidResources(
                        segments = segments,
                        path = path,
                    )
                }

                ResourceFileType.IOS_STRINGS -> {
                    exportIosResources(
                        segments = segments,
                        path = path,
                    )
                }

                else -> Unit
            }
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

    companion object {
        const val KEY_TOOLBAR_SLOT = "ToolbarSlot"
        const val KEY_MESSAGE_LIST_SLOT = "MessageListSlot"
    }
}
