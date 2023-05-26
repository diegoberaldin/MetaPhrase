package projectslist

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.ProjectModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface ProjectListComponent {
    val uiState: StateFlow<ProjectListUiState>
    val projectSelected: SharedFlow<ProjectModel>
    fun openProject(value: ProjectModel)
    fun delete(value: ProjectModel)

    object Factory {
        fun create(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ): ProjectListComponent = DefaultProjectListComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            projectRepository = getByInjection(),
            notificationCenter = getByInjection(),
        )
    }
}
