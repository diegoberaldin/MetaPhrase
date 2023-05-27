package projectslist.ui

import data.ProjectModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ProjectListComponent {
    val uiState: StateFlow<ProjectListUiState>
    val projectSelected: SharedFlow<ProjectModel>
    fun openProject(value: ProjectModel)
    fun delete(value: ProjectModel)
}
