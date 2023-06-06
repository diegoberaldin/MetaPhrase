package main.ui

import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.child
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.essenty.lifecycle.doOnStop
import common.coroutines.CoroutineDispatcherProvider
import common.notification.NotificationCenter
import common.utils.asFlow
import common.utils.getByInjection
import data.LanguageModel
import data.ProjectModel
import data.ResourceFileType
import intro.ui.IntroComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mainsettings.ui.SettingsComponent
import projects.ui.ProjectsComponent
import projectscreate.ui.CreateProjectComponent
import projectstatistics.ui.StatisticsComponent
import repository.repo.ProjectRepository
import translationmemory.usecase.ClearTmUseCase
import translationmemory.usecase.ImportTmxUseCase
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    projectRepository: ProjectRepository,
    private val importFromTmx: ImportTmxUseCase,
    private val clearTranslationMemory: ClearTmUseCase,
    private val notificationCenter: NotificationCenter,
) : RootComponent, ComponentContext by componentContext {

    companion object {
        const val KEY_MAIN_SLOT = "MainSlot"
        const val KEY_DIALOG_SLOT = "DialogSlot"
    }

    private lateinit var viewModelScope: CoroutineScope

    private val mainNavigation = SlotNavigation<RootComponent.Config>()
    private val dialogNavigation = SlotNavigation<RootComponent.DialogConfig>()
    private lateinit var activeProject: StateFlow<ProjectModel?>
    private lateinit var isEditing: StateFlow<Boolean>
    private lateinit var currentLanguage: StateFlow<LanguageModel?>
    private val isLoading = MutableStateFlow(false)
    private var observeProjectsJob: Job? = null

    override val main: Value<ChildSlot<RootComponent.Config, *>> = childSlot(
        source = mainNavigation,
        key = KEY_MAIN_SLOT,
        childFactory = ::createMainChild,
    )
    override val dialog: Value<ChildSlot<RootComponent.DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = KEY_DIALOG_SLOT,
        childFactory = ::createDialogChild,
    )
    override lateinit var uiState: StateFlow<RootUiState>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                activeProject = main.asFlow<ProjectsComponent>(true, timeout = Duration.INFINITE).flatMapLatest {
                    it?.activeProject ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
                isEditing = main.asFlow<ProjectsComponent>(true, timeout = Duration.INFINITE).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = false,
                )
                currentLanguage = main.asFlow<ProjectsComponent>(true, timeout = Duration.INFINITE).flatMapLatest {
                    it?.currentLanguage ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
                uiState = combine(
                    activeProject,
                    isEditing,
                    currentLanguage,
                    isLoading,
                ) { activeProject, isEditing, currentLanguage, isLoading ->
                    RootUiState(
                        activeProject = activeProject,
                        isEditing = isEditing,
                        isLoading = isLoading,
                        currentLanguage = currentLanguage,

                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = RootUiState(),
                )
            }
            doOnStart {
                if (observeProjectsJob == null) {
                    observeProjectsJob = viewModelScope.launch(dispatchers.io) {
                        projectRepository.observeAll().map { it.isEmpty() }.distinctUntilChanged().collect { isEmpty ->
                            withContext(dispatchers.main) {
                                if (isEmpty) {
                                    mainNavigation.activate(RootComponent.Config.Intro)
                                } else {
                                    mainNavigation.activate(RootComponent.Config.Projects)
                                }
                            }
                        }
                    }
                }
                viewModelScope.launch {
                    notificationCenter.events.filter { it is NotificationCenter.Event.ShowProgress }.onEach { evt ->
                        when (evt) {
                            is NotificationCenter.Event.ShowProgress -> {
                                isLoading.value = evt.visible
                            }
                        }
                    }.launchIn(this)
                }
            }
            doOnStop {
                observeProjectsJob?.cancel()
                observeProjectsJob = null
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private fun createMainChild(config: RootComponent.Config, componentContext: ComponentContext): Any = when (config) {
        RootComponent.Config.Projects -> getByInjection<ProjectsComponent>(componentContext, coroutineContext)
        else -> getByInjection<IntroComponent>(componentContext, coroutineContext)
    }

    private fun createDialogChild(config: RootComponent.DialogConfig, componentContext: ComponentContext): Any =
        when (config) {
            RootComponent.DialogConfig.NewDialog -> {
                getByInjection<CreateProjectComponent>(componentContext, coroutineContext).apply {
                    done.onEach { projectId ->
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                        if (projectId != null) {
                            when (val child = main.asFlow<Any>().firstOrNull()) {
                                is ProjectsComponent -> {
                                    child.open(projectId)
                                }

                                is IntroComponent -> {
                                    withContext(dispatchers.main) {
                                        mainNavigation.activate(RootComponent.Config.Projects)
                                    }
                                    delay(100)
                                    main.asFlow<ProjectsComponent>().firstOrNull()?.open(projectId)
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.EditDialog -> {
                getByInjection<CreateProjectComponent>(componentContext, coroutineContext).apply {
                    projectId = activeProject.value?.id ?: 0
                    done.onEach {
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.StatisticsDialog -> {
                getByInjection<StatisticsComponent>(componentContext, coroutineContext).apply {
                    projectId = activeProject.value?.id ?: 0
                }
            }

            is RootComponent.DialogConfig.SettingsDialog -> {
                getByInjection<SettingsComponent>(componentContext, coroutineContext)
            }

            else -> Unit
        }

    override fun openEditProject() {
        val projectId = activeProject.value?.id
        if (projectId != null) {
            dialogNavigation.activate(RootComponent.DialogConfig.EditDialog)
        }
    }

    override fun openNewDialog() {
        dialogNavigation.activate(RootComponent.DialogConfig.NewDialog)
    }

    override fun closeDialog() {
        dialogNavigation.activate(RootComponent.DialogConfig.None)
    }

    override fun closeCurrentProject() {
        (main.child?.instance as? ProjectsComponent)?.closeCurrentProject()
    }

    override fun openImportDialog(type: ResourceFileType) {
        dialogNavigation.activate(RootComponent.DialogConfig.ImportDialog(type))
    }

    override fun openExportDialog(type: ResourceFileType) {
        dialogNavigation.activate(RootComponent.DialogConfig.ExportDialog(type))
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    override fun moveToPreviousSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNextSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.addSegment()
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.deleteSegment()
        }
    }

    override fun openStatistics() {
        dialogNavigation.activate(RootComponent.DialogConfig.StatisticsDialog)
    }

    override fun openSettings() {
        dialogNavigation.activate(RootComponent.DialogConfig.SettingsDialog)
    }

    override fun openExportTmxDialog() {
        dialogNavigation.activate(RootComponent.DialogConfig.ExportTmxDialog)
    }

    override fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.exportTmx(path = path)
        }
    }

    override fun openImportTmxDialog() {
        dialogNavigation.activate(RootComponent.DialogConfig.ImportTmxDialog)
    }

    override fun importTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            importFromTmx(path = path)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun clearTm() {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            clearTranslationMemory()
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun validatePlaceholders() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.validatePlaceholders()
        }
    }

    override fun insertBestMatch() {
        viewModelScope.launch(dispatchers.io) {
            main.asFlow<ProjectsComponent>().firstOrNull()?.insertBestMatch()
        }
    }
}
