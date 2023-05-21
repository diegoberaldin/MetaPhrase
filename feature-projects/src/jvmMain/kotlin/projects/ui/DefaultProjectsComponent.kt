package projects.ui

import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import common.coroutines.CoroutineDispatcherProvider
import common.keystore.TemporaryKeyStore
import common.utils.observeChildStack
import common.utils.observeNullableChildStack
import data.ResourceFileType
import data.ProjectModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import projectslist.ProjectListComponent
import repository.local.ProjectRepository
import translate.ui.TranslateComponent
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultProjectsComponent(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val keyStore: TemporaryKeyStore,
    private val projectRepository: ProjectRepository,
) : ProjectsComponent, ComponentContext by componentContext {

    private val _activeProject = MutableStateFlow<ProjectModel?>(null)
    private lateinit var _isEditing: StateFlow<Boolean>
    private val navigation = StackNavigation<ProjectsComponent.Config>()
    private lateinit var viewModelScope: CoroutineScope
    private var observeProjectToOpenJob: Job? = null

    private val _childStack = childStack(
        source = navigation,
        initialConfiguration = ProjectsComponent.Config.List,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    override val childStack: Value<ChildStack<ProjectsComponent.Config, *>> = _childStack
    override val activeProject = _activeProject.asStateFlow()
    override val isEditing get() = _isEditing

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                observeChildStack<ProjectListComponent>(childStack).flatMapLatest { it.projectSelected }
                    .onEach { project ->
                        openProject(project.id)
                    }.launchIn(viewModelScope)
                _isEditing = observeNullableChildStack<TranslateComponent>(childStack).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = false,
                )
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    val lastOpenedProjectId = keyStore.get("lastOpenedProject", 0)
                    if (lastOpenedProjectId > 0) {
                        openProject(lastOpenedProjectId)
                    }
                }
                if (observeProjectToOpenJob == null) {
                    observeProjectToOpenJob = viewModelScope.launch {
                        channelFlow {
                            while (true) {
                                if (!isActive) {
                                    break
                                }
                                val projectId = keyStore.get("newProjectToOpen", 0)
                                if (projectId > 0) {
                                    trySend(projectId)
                                }
                                delay(1_000)
                            }
                        }.collect {
                            keyStore.save("newProjectToOpen", 0)

                            when (val conf = childStack.value.active.configuration) {
                                is ProjectsComponent.Config.Detail -> {
                                    val childComp = (childStack.value.active.instance as TranslateComponent)
                                    if (childComp.projectId != conf.projectId) {
                                        childComp.projectId = conf.projectId
                                    }
                                }

                                else -> {
                                    openProject(it)
                                }
                            }
                        }
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private suspend fun openProject(projectId: Int) {
        if (_activeProject.value?.id == projectId) {
            return
        }

        withContext(dispatchers.io) {
            keyStore.save("lastOpenedProject", projectId)
        }
        _activeProject.value = projectRepository.getById(projectId)
        withContext(dispatchers.main) {
            navigation.push(ProjectsComponent.Config.Detail(projectId = projectId))
        }
    }

    private fun createChild(config: ProjectsComponent.Config, componentContext: ComponentContext): Any = when (config) {
        is ProjectsComponent.Config.List -> ProjectListComponent.Factory.create(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
        )

        is ProjectsComponent.Config.Detail -> {
            TranslateComponent.Factory.create(
                componentContext = componentContext,
                coroutineContext = coroutineContext,
            ).apply {
                projectId = config.projectId
            }
        }
    }

    override fun closeCurrentProject() {
        viewModelScope.launch(dispatchers.io) {
            keyStore.save("lastOpenedProject", 0)
        }
        _activeProject.value = null
        navigation.pop()
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(_childStack).firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(_childStack).firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(_childStack).firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(_childStack).firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(_childStack).firstOrNull()?.endEditing()
        }
    }
}
