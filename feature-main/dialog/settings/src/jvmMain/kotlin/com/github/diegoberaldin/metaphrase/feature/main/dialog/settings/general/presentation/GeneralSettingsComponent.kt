package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

interface GeneralSettingsComponent :
    MviModel<GeneralSettingsComponent.Intent, GeneralSettingsComponent.UiState, GeneralSettingsComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Set language.
         *
         * @param value language to set
         */
        data class SetLanguage(val value: LanguageModel) : Intent

        /**
         * Set similarity threshold
         *
         * @param value value to set
         */
        data class SetSimilarity(val value: String) : Intent

        /**
         * Enabled/disabled the spelling check.
         *
         * @param value true to enable, false otherwise
         */
        data class SetSpellcheckEnabled(val value: Boolean) : Intent
    }

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * UI state for general settings.
     *
     * @property availableLanguages available app languages
     * @property currentLanguage current language
     * @property isLoading true if there is a background operation in progress
     * @property similarityThreshold current similarity threshold
     * @property spellcheckEnabled true if spelling check is enabled
     * @property appVersion application version
     * @constructor Create [UiState]
     */
    data class UiState(
        val availableLanguages: List<LanguageModel> = emptyList(),
        val currentLanguage: LanguageModel? = null,
        val isLoading: Boolean = false,
        val similarityThreshold: String = "",
        val spellcheckEnabled: Boolean = false,
        val appVersion: String = "",
    )
}
