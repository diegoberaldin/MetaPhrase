package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel

data class CreateProjectUiState(
    val name: String = "",
    val nameError: String = "",
    val isLoading: Boolean = false,
)

data class CreateProjectLanguagesUiState(
    val languages: List<LanguageModel> = emptyList(),
    val languagesError: String = "",
    val availableLanguages: List<LanguageModel> = emptyList(),
)