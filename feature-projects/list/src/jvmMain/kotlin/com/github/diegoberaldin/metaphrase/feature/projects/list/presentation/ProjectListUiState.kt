package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel

data class ProjectListUiState(val projects: List<RecentProjectModel> = emptyList())
