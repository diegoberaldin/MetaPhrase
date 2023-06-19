package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnResume
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultProjectListComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: RecentProjectRepository,
    private val openProject: OpenProjectUseCase,
    private val notificationCenter: NotificationCenter,
) : ProjectListComponent, ComponentContext by componentContext {

    private val projects = MutableStateFlow<List<RecentProjectModel>>(emptyList())
    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<ProjectListComponent.DialogConfiguration>()

    override lateinit var uiState: StateFlow<ProjectListUiState>
    override val projectSelected = MutableSharedFlow<ProjectModel>()
    override val dialog: Value<ChildSlot<ProjectListComponent.DialogConfiguration, *>> = childSlot(
        source = dialogNavigation,
        key = "ProjectListDialogSlot",
        childFactory = { _, _ ->
            Unit
        },
    )

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
            doOnResume {
                viewModelScope.launch(dispatchers.io) {
                    projectRepository.observeAll().onEach { values ->
                        projects.value = values
                    }.launchIn(this)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun openRecent(value: RecentProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val path = value.path
            val project = openProject(path = path)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
            if (project != null) {
                projectSelected.emit(project)
            } else {
                projectRepository.delete(value)

                withContext(dispatchers.main) {
                    dialogNavigation.activate(ProjectListComponent.DialogConfiguration.OpenError)
                }
            }
        }
    }

    override fun removeFromRecent(value: RecentProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            projectRepository.delete(value)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ProjectListComponent.DialogConfiguration.None)
        }
    }
}
