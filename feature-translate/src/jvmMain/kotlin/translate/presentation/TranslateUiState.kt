package translate.presentation

import projectdata.ProjectModel

data class TranslateUiState(
    val project: ProjectModel? = null,
    val unitCount: Int = 0,
)
