package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.ui

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel

data class SettingsUiState(
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentLanguage: LanguageModel? = null,
    val similarityThreshold: String = "",
    val spellcheckEnabled: Boolean = false,
    val appVersion: String = "",
)
