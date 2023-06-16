package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ProjectListComponent {
    val uiState: StateFlow<ProjectListUiState>
    val projectSelected: SharedFlow<ProjectModel>
    fun openProject(value: ProjectModel)
    fun delete(value: ProjectModel)
}
