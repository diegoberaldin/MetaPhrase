package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * New glossary term component contract.
 */
interface NewGlossaryTermComponent :
    MviModel<NewGlossaryTermComponent.ViewIntent, NewGlossaryTermComponent.UiState, NewGlossaryTermComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Set source term.
         *
         * @param value term to set
         */
        data class SetSourceTerm(val value: String) : ViewIntent

        /**
         * Set target term.
         *
         * @param value term to set
         */
        data class SetTargetTerm(val value: String) : ViewIntent

        /**
         * Confirm the inserted term pair.
         */
        object Submit : ViewIntent
    }

    /**
     * New glossary term UI state.
     *
     * @property sourceTerm source term
     * @property sourceTermError error for the source term field
     * @property targetTerm target get
     * @property targetTermError error for the target term field
     * @constructor Create [NewGlossaryTermUiState]
     */
    data class UiState(
        val sourceTerm: String = "",
        val sourceTermError: String = "",
        val targetTerm: String = "",
        val targetTermError: String = "",
    )

    sealed interface Effect {
        /**
         * Event emitted after successful submission.
         */
        data class Done(val pair: GlossaryTermPair) : Effect
    }
}
