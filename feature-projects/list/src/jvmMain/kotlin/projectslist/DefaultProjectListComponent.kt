package projectslist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.essenty.lifecycle.doOnStop
import common.coroutines.CoroutineDispatcherProvider
import data.ProjectModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.local.ProjectRepository
import kotlin.coroutines.CoroutineContext

internal class DefaultProjectListComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
) : ProjectListComponent, ComponentContext by componentContext {

    private val projects = MutableStateFlow<List<ProjectModel>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope
    private var observeProjectsJob: Job? = null

    override lateinit var uiState: StateFlow<ProjectListUiState>
    override val projectSelected = MutableSharedFlow<ProjectModel>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = projects.map {
                    ProjectListUiState(projects = it)
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ProjectListUiState(),
                )
            }
            doOnStart {
                if (observeProjectsJob == null) {
                    observeProjectsJob = viewModelScope.launch(dispatchers.io) {
                        projectRepository.observeAll().collect { values ->
                            projects.value = values
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

    override fun openProject(value: ProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            projectSelected.emit(value)
        }
    }

    override fun delete(value: ProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            projectRepository.delete(value)
        }
    }
}
