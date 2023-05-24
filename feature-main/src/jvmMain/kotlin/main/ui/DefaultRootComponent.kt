package main.ui

import StatisticsComponent
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
import common.utils.observeChildSlot
import common.utils.observeNullableChildSlot
import data.LanguageModel
import data.ProjectModel
import data.ResourceFileType
import intro.ui.IntroComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import projects.ui.ProjectsComponent
import projectscreate.ui.CreateProjectComponent
import repository.local.ProjectRepository
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultRootComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    projectRepository: ProjectRepository,
) : RootComponent, ComponentContext by componentContext {

    companion object {
        const val KEY_MAIN_SLOT = "MainSlot"
        const val KEY_DIALOG_SLOT = "DialogSlot"
    }

    private lateinit var viewModelScope: CoroutineScope

    private val mainNavigation = SlotNavigation<RootComponent.Config>()
    private val dialogNavigation = SlotNavigation<RootComponent.DialogConfig>()

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

    override lateinit var activeProject: StateFlow<ProjectModel?>
    override lateinit var isEditing: StateFlow<Boolean>
    override lateinit var currentLanguage: StateFlow<LanguageModel?>
    private var observeProjectsJob: Job? = null

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                activeProject = observeNullableChildSlot<ProjectsComponent>(main).flatMapLatest {
                    it?.activeProject ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
                isEditing = observeNullableChildSlot<ProjectsComponent>(main).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = false,
                )
                currentLanguage = observeNullableChildSlot<ProjectsComponent>(main).flatMapLatest {
                    it?.currentLanguage ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
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
        RootComponent.Config.Projects -> ProjectsComponent.Factory.create(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
        )

        else -> IntroComponent.Factory.create(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
        )
    }

    private fun createDialogChild(config: RootComponent.DialogConfig, componentContext: ComponentContext): Any =
        when (config) {
            RootComponent.DialogConfig.NewDialog -> {
                CreateProjectComponent.Factory.create(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                ).apply {
                    done.onEach { projectId ->
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                        if (projectId != null) {
                            when (val child = observeChildSlot<Any>(main).first()) {
                                is ProjectsComponent -> {
                                    child.open(projectId)
                                }

                                is IntroComponent -> {
                                    withContext(dispatchers.main) {
                                        mainNavigation.activate(RootComponent.Config.Projects)
                                    }
                                    delay(100)
                                    observeChildSlot<ProjectsComponent>(main).firstOrNull()?.open(projectId)
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.EditDialog -> {
                CreateProjectComponent.Factory.create(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                ).apply {
                    projectId = activeProject.value?.id ?: 0
                    done.onEach {
                        withContext(dispatchers.main) {
                            closeDialog()
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is RootComponent.DialogConfig.StatisticsDialog -> {
                StatisticsComponent.Factory.create(
                    componentContext = componentContext,
                    coroutineContext = coroutineContext,
                ).apply {
                    projectId = activeProject.value?.id ?: 0
                }
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
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    override fun moveToPreviousSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNextSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.addSegment()
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildSlot<ProjectsComponent>(main).firstOrNull()?.deleteSegment()
        }
    }

    override fun openStatistics() {
        dialogNavigation.activate(RootComponent.DialogConfig.StatisticsDialog)
    }
}
