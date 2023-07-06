package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

/**
 * Settings component contract.
 */
interface SettingsComponent :
    MviModel<SettingsComponent.ViewIntent, SettingsComponent.UiState, SettingsComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Set language.
         *
         * @param value language to set
         */
        data class SetLanguage(val value: LanguageModel) : ViewIntent

        /**
         * Set similarity threshold
         *
         * @param value value to set
         */
        data class SetSimilarity(val value: String) : ViewIntent

        /**
         * Enabled/disabled the spelling check.
         *
         * @param value true to enable, false otherwise
         */
        data class SetSpellcheckEnabled(val value: Boolean) : ViewIntent

        /**
         * Set machine translation provider.
         *
         * @param index provider index.
         */
        data class SetMachineTranslationProvider(val index: Int) : ViewIntent

        /**
         * Set machine translation API key.
         *
         * @param value API key to set
         */
        data class SetMachineTranslationKey(val value: String) : ViewIntent

        /**
         * Open the login sub-dialog.
         */
        object OpenLoginDialog : ViewIntent

        /**
         * Close the current sub-dialog.
         */
        object CloseDialog : ViewIntent

        /**
         * Generate a machine translation API key.
         *
         * @param username user's username
         * @param password user's password
         */
        data class GenerateMachineTranslationKey(val username: String, val password: String) : ViewIntent
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
     * @property availableProviders available Machine Translation providers
     * @property currentProvider current MT provider
     * @property key API key
     * @constructor Create [SettingsUiState]
     */
    data class UiState(
        val availableLanguages: List<LanguageModel> = emptyList(),
        val currentLanguage: LanguageModel? = null,
        val isLoading: Boolean = false,
        val similarityThreshold: String = "",
        val spellcheckEnabled: Boolean = false,
        val appVersion: String = "",
        val availableProviders: List<MachineTranslationProvider> = emptyList(),
        val currentProvider: MachineTranslationProvider? = null,
        val key: String = "",
    )

    /**
     * Available sub-dialog configurations.
     */
    sealed interface DialogConfig : Parcelable {
        /**
         * None (close currrent dialog)
         */
        @Parcelize
        object None : DialogConfig

        /**
         * Login dialog
         */
        @Parcelize
        object Login : DialogConfig
    }

    /**
     * Current sub-dialog configuration.
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>
}
