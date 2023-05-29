package mainsettings.ui

import data.LanguageModel

data class SettingsUiState(
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentLanguage: LanguageModel? = null,
    val similarityThreshold: String = "",
    val appVersion: String = "",
)
