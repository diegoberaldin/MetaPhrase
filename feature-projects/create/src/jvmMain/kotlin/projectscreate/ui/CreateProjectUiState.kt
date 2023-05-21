package projectscreate.ui

import data.LanguageModel

data class CreateProjectUiState(
    val name: String = "",
    val nameError: String = "",
)

data class CreateProjectLanguagesUiState(
    val languages: List<LanguageModel> = emptyList(),
    val languagesError: String = "",
    val availableLanguages: List<LanguageModel> = emptyList(),
)