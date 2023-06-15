package main.presentation

import projectdata.LanguageModel
import projectdata.ProjectModel

data class RootUiState(
    val activeProject: ProjectModel? = null,
    val isEditing: Boolean = false,
    val currentLanguage: LanguageModel? = null,
    val isLoading: Boolean = false,
)
