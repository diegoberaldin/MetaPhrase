package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

data class TranslateUiState(
    val project: ProjectModel? = null,
    val unitCount: Int = 0,
    val needsSaving: Boolean = false,
)
