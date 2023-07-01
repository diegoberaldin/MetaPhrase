package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

data class CreateProjectUiState(
    val name: String = "",
    val nameError: String = "",
    val isLoading: Boolean = false,
    val languages: List<LanguageModel> = emptyList(),
    val languagesError: String = "",
    val availableLanguages: List<LanguageModel> = emptyList(),
)
