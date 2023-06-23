package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

/**
 * UI state for language data.
 *
 * @property availableLanguages available app languages
 * @property currentLanguage current language
 * @constructor Create [SettingsLanguageUiState]
 */
data class SettingsLanguageUiState(
    val availableLanguages: List<LanguageModel> = emptyList(),
    val currentLanguage: LanguageModel? = null,
)

/**
 * UI state for general settings.
 *
 * @property isLoading true if there is a background operation in progress
 * @property similarityThreshold current similarity threshold
 * @property spellcheckEnabled true if spelling check is enabled
 * @property appVersion application version
 * @constructor Create [SettingsUiState]
 */
data class SettingsUiState(
    val isLoading: Boolean = false,
    val similarityThreshold: String = "",
    val spellcheckEnabled: Boolean = false,
    val appVersion: String = "",
)

/**
 * UI state for machine transaltion
 *
 * @property availableProviders available Machine Translation providers
 * @property currentProvider current MT provider
 * @property key API key
 * @constructor Create [SettingsMachineTranslationUiState]
 */
data class SettingsMachineTranslationUiState(
    val availableProviders: List<MachineTranslationProvider> = emptyList(),
    val currentProvider: MachineTranslationProvider? = null,
    val key: String = "",
)
