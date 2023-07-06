package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * New segment component contract.
 */
interface NewSegmentComponent :
    MviModel<NewSegmentComponent.ViewIntent, NewSegmentComponent.UiState, NewSegmentComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Set the message key.
         *
         * @param value Value
         */
        data class SetKey(val value: String) : ViewIntent

        /**
         * Set the message text.
         *
         * @param value Value
         */
        data class SetText(val value: String) : ViewIntent

        /**
         * Close the dialog.
         */
        object Close : ViewIntent

        /**
         * Confirm creation of segment with current key and message.
         */
        object Submit : ViewIntent
    }

    /**
     * New segment dialog UI state.
     *
     * @property key message key
     * @property keyError error for the key field
     * @property text message text
     * @property textError error for the text field
     * @property isLoading boolean indicating whether a background operation is in progress
     * @constructor Create [UiState]
     */
    data class UiState(
        val key: String = "",
        val keyError: String = "",
        val text: String = "",
        val textError: String = "",
        val isLoading: Boolean = false,
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        data class Done(val segment: SegmentModel?) : Effect
    }

    /**
     * Current project ID
     */
    var projectId: Int

    /**
     * Language for which the message should be added
     */
    var language: LanguageModel
}
