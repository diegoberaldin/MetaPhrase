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
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class DefaultProjectListComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<ProjectListComponent.Intent, ProjectListComponent.UiState, ProjectListComponent.Effect> = DefaultMviModel(
        ProjectListComponent.UiState(),
    ),
    private val projectRepository: RecentProjectRepository,
    private val openProject: OpenProjectUseCase,
    private val notificationCenter: NotificationCenter,
) : ProjectListComponent,
    MviModel<ProjectListComponent.Intent, ProjectListComponent.UiState, ProjectListComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope
    private val dialogNavigation = SlotNavigation<ProjectListComponent.DialogConfiguration>()

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
            }
            doOnResume {
                viewModelScope.launch(dispatchers.io) {
                    projectRepository.observeAll().onEach { values ->
                        mvi.updateState { it.copy(projects = values) }
                    }.launchIn(this)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: ProjectListComponent.Intent) {
        when (intent) {
            ProjectListComponent.Intent.CloseDialog -> closeDialog()
            is ProjectListComponent.Intent.OpenRecent -> openRecent(intent.value)
            is ProjectListComponent.Intent.RemoveFromRecent -> removeFromRecent(intent.value)
        }
    }

    private fun openRecent(value: RecentProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val path = value.path
            val project = openProject(path = path)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
            if (project != null) {
                mvi.emitEffect(ProjectListComponent.Effect.ProjectSelected(project))
            } else {
                projectRepository.delete(value)

                withContext(dispatchers.main) {
                    dialogNavigation.activate(ProjectListComponent.DialogConfiguration.OpenError)
                }
            }
        }
    }

    private fun removeFromRecent(value: RecentProjectModel) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            projectRepository.delete(value)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    private fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(ProjectListComponent.DialogConfiguration.None)
        }
    }
}
