package com.github.diegoberaldin.metaphrase.feature.main.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

data class RootUiState(
    val activeProject: ProjectModel? = null,
    val isEditing: Boolean = false,
    val currentLanguage: LanguageModel? = null,
    val isLoading: Boolean = false,
)