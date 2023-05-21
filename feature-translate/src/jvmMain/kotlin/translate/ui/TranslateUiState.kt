package translate.ui

import data.ProjectModel

data class TranslateUiState(
    val project: ProjectModel? = null,
    val unitCount: Int = 0,
)
