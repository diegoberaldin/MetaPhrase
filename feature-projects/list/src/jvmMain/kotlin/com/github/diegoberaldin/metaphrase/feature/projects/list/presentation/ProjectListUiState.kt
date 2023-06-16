package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

data class ProjectListUiState(val projects: List<ProjectModel> = emptyList())
