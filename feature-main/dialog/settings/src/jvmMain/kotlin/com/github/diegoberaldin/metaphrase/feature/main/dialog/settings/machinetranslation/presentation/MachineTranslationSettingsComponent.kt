package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

interface MachineTranslationSettingsComponent :
    MviModel<MachineTranslationSettingsComponent.Intent, MachineTranslationSettingsComponent.UiState, MachineTranslationSettingsComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Set machine translation provider.
         *
         * @param index provider index.
         */
        data class SetMachineTranslationProvider(val index: Int) : Intent

        /**
         * Set machine translation API key.
         *
         * @param value API key to set
         */
        data class SetMachineTranslationKey(val value: String) : Intent

        /**
         * Open the login sub-dialog.
         */
        object OpenLoginDialog : Intent

        /**
         * Close the current sub-dialog.
         */
        object CloseDialog : Intent

        /**
         * Generate a machine translation API key.
         *
         * @param username user's username
         * @param password user's password
         */
        data class GenerateMachineTranslationKey(val username: String, val password: String) : Intent
    }

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * UI state for machine translation settings.
     *
     * @property isLoading true if there is a background operation in progress
     * @property availableProviders available Machine Translation providers
     * @property currentProvider current MT provider
     * @property key API key
     * @property supportsKeyGeneration true if the machine translation provider supports generating keys
     * @constructor Create [UiState]
     */
    data class UiState(
        val isLoading: Boolean = false,
        val availableProviders: List<MachineTranslationProvider> = emptyList(),
        val currentProvider: MachineTranslationProvider? = null,
        val key: String = "",
        val supportsKeyGeneration: Boolean = false,
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
