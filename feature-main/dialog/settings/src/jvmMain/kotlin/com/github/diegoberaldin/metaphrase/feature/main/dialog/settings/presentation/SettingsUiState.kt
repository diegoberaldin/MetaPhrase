package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

data class SettingsLanguageUiState(
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentLanguage: LanguageModel? = null,
)

data class SettingsUiState(
    val isLoading: Boolean = false,
    val similarityThreshold: String = "",
    val spellcheckEnabled: Boolean = false,
    val appVersion: String = "",
)

data class SettingsMachineTranslationUiState(
    val availableProviders: List<MachineTranslationProvider> = emptyList(),
    val currentProvider: MachineTranslationProvider? = null,
    val key: String = "",
)
