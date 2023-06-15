package projectslist.presentation

import projectdata.ProjectModel

data class ProjectListUiState(val projects: List<ProjectModel> = emptyList())
