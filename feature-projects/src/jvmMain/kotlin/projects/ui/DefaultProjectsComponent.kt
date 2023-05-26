package projects.ui

import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import common.coroutines.CoroutineDispatcherProvider
import common.keystore.TemporaryKeyStore
import common.utils.observeChildStack
import common.utils.observeNullableChildStack
import data.LanguageModel
import data.ProjectModel
import data.ResourceFileType
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    private val navigation = StackNavigation<ProjectsComponent.Config>()
    private lateinit var viewModelScope: CoroutineScope

    override val childStack = childStack(
        source = navigation,
        initialConfiguration = ProjectsComponent.Config.List,
        handleBackButton = true,
        childFactory = ::createChild,
    )
    override val activeProject = MutableStateFlow<ProjectModel?>(null)
    override lateinit var isEditing: StateFlow<Boolean>
    override lateinit var currentLanguage: StateFlow<LanguageModel?>

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                observeChildStack<ProjectListComponent>(childStack).flatMapLatest { it.projectSelected }
                    .onEach { project ->
                        openProject(project.id)
                    }.launchIn(viewModelScope)
                isEditing = observeNullableChildStack<TranslateComponent>(childStack).flatMapLatest {
                    it?.isEditing ?: snapshotFlow { false }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = false,
                )
                currentLanguage = observeNullableChildStack<TranslateComponent>(childStack).flatMapLatest {
                    it?.currentLanguage ?: snapshotFlow { null }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = null,
                )
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    val lastOpenedProjectId = keyStore.get("lastOpenedProject", 0)
                    if (lastOpenedProjectId > 0) {
                        openProject(lastOpenedProjectId)
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun open(projectId: Int) {
        when (val conf = childStack.value.active.configuration) {
            is ProjectsComponent.Config.Detail -> {
                val childComp = (childStack.value.active.instance as TranslateComponent)
                if (childComp.projectId != conf.projectId) {
                    childComp.projectId = conf.projectId
                }
            }

            else -> {
                viewModelScope.launch(dispatchers.io) {
                    openProject(projectId)
                }
            }
        }
    }

    private suspend fun openProject(projectId: Int) {
        if (activeProject.value?.id == projectId) {
            return
        }

        withContext(dispatchers.io) {
            keyStore.save("lastOpenedProject", projectId)
        }
        activeProject.value = projectRepository.getById(projectId)
        withContext(dispatchers.main) {
            navigation.push(ProjectsComponent.Config.Detail(projectId = projectId))
        }
    }

    private fun createChild(config: ProjectsComponent.Config, componentContext: ComponentContext): Any = when (config) {
        is ProjectsComponent.Config.List -> ProjectListComponent.newInstance(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
        )

        is ProjectsComponent.Config.Detail -> {
            TranslateComponent.newInstance(
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
        activeProject.value = null
        navigation.pop()
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.import(
                path = path,
                type = type,
            )
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.export(
                path = path,
                type = type,
            )
        }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.addSegment()
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            observeChildStack<TranslateComponent>(childStack).firstOrNull()?.deleteSegment()
        }
    }
}
