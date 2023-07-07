package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.ValidationContent

/**
 * Validation component.
 */
interface ValidateComponent :
    MviModel<ValidateComponent.ViewIntent, ValidateComponent.UiState, ValidateComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Load a list of invalid placeholder references.
         *
         * @param projectId Project ID
         * @param languageId Language ID
         * @param invalidKeys Invalid message keys
         */
        data class LoadInvalidPlaceholders(val projectId: Int, val languageId: Int, val invalidKeys: List<String>) :
            ViewIntent

        /**
         * Load a list of spelling mistake references.
         *
         * @param errors list of spelling errors (message key to list of incorrect words)
         */
        data class LoadSpellingMistakes(val errors: Map<String, List<String>>) : ViewIntent

        /**
         * Clear the panel content.
         */
        object Clear : ViewIntent

        /**
         * Select an reference, triggering [Effect.Selection].
         *
         * @param value index of the reference to select
         */
        data class SelectItem(val value: Int) : ViewIntent
    }

    /**
     * UI state of the validation panel.
     *
     * @property content panel content
     * @constructor Create [UiState]
     */
    data class UiState(
        val content: ValidationContent? = null,
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Events triggered when a reference to a message is selected with the key of the message
         */
        data class Selection(val key: String) : Effect
    }
}
