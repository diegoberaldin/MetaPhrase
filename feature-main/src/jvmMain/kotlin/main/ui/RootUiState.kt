package main.ui

import data.LanguageModel
import data.ProjectModel

data class RootUiState(
    val activeProject: ProjectModel? = null,
    val isEditing: Boolean = false,
    val currentLanguage: LanguageModel? = null,
    val isLoading: Boolean = false,
)
