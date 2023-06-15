package dialogsettings.ui

import projectdata.LanguageModel

data class SettingsUiState(
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentLanguage: LanguageModel? = null,
    val similarityThreshold: String = "",
    val spellcheckEnabled: Boolean = false,
    val appVersion: String = "",
)
