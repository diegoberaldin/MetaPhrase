package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Settings component.
 */
interface SettingsComponent {
    /**
     * General UI state.
     */
    val uiState: StateFlow<SettingsUiState>

    /**
     * Current sub-dialog configuration.
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>

    /**
     * Set language.
     *
     * @param value language to set
     */
    fun setLanguage(value: LanguageModel)

    /**
     * Set similarity threshold
     *
     * @param value value to set
     */
    fun setSimilarity(value: String)

    /**
     * Enabled/disabled the spelling check.
     *
     * @param value true to enable, false otherwise
     */
    fun setSpellcheckEnabled(value: Boolean)

    /**
     * Set machine translation provider.
     *
     * @param index provider index.
     */
    fun setMachineTranslationProvider(index: Int)

    /**
     * Set machine translation API key.
     *
     * @param value API key to set
     */
    fun setMachineTranslationKey(value: String)

    /**
     * Open the login sub-dialog.
     */
    fun openLoginDialog()

    /**
     * Close the current sub-dialog.
     */
    fun closeDialog()

    /**
     * Generate a machine translation API key.
     *
     * @param username user's username
     * @param password user's password
     */
    fun generateMachineTranslationKey(username: String, password: String)

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
}
