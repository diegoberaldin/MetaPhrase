package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * Machine translation component contract.
 */
interface MachineTranslationComponent :
    MviModel<MachineTranslationComponent.ViewIntent, MachineTranslationComponent.UiState, MachineTranslationComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Clear the content of the panel.
         */
        object Clear : ViewIntent

        /**
         * Load the data for the message with a given key. No suggestion is retrieved until the [retrieve] method is called.
         * This is intended to reduce the request number and not exceed the service quota.
         *
         * @param key message key
         * @param projectId Project ID
         * @param languageId Language ID
         */
        data class Load(val key: String, val projectId: Int, val languageId: Int) : ViewIntent

        /**
         * Retrieve a suggestion from the MT provider. The [load] method should be called to set the language and the source
         * message that will be translated.
         */
        object Retrieve : ViewIntent

        /**
         * Signal the user intention to copy the suggestion into the editor, triggering a [Effect.CopySource] event.
         */
        object InsertTranslation : ViewIntent

        /**
         * Signal the user intention to copy the content of the target field in the translation editor into the suggestion
         * field, triggering a [Effect.CopyTarget] event.
         */
        object CopyTarget : ViewIntent

        /**
         * Set a value for the suggestion, when the event is initiated by the user.
         *
         * @param value suggestion to set
         */
        data class SetTranslation(val value: String) : ViewIntent

        /**
         * Programmatically update the value of the suggestion.
         *
         * @param value suggestion to set
         */
        data class CopyTranslation(val value: String) : ViewIntent

        /**
         * Share the suggestion field content with the remote MT provider.
         */
        object Share : ViewIntent
    }

    /**
     * Machine translation panel UI state.
     *
     * @property isLoading indication whether there is a background operation in progress
     * @property translation suggestion from MT
     * @property updateTextSwitch flag to trigger suggestion updates programmatically
     * @constructor Create [UiState]
     */
    data class UiState(
        val isLoading: Boolean = false,
        val translation: String = "",
        val updateTextSwitch: Boolean = false,
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Events triggered when the content of the suggestion field from MT needs to be copied into the translation editor.
         */
        data class CopySource(val value: String) : Effect

        /**
         * Events triggered when the content of the translation editor needs to be copied to the suggestion field.
         */
        object CopyTarget : Effect

        /**
         * Events triggered after a message share with the MT provider.
         *
         * @property successful whether the operation was successful
         * @constructor Create [Share]
         */
        data class Share(val successful: Boolean) : Effect
    }
}
