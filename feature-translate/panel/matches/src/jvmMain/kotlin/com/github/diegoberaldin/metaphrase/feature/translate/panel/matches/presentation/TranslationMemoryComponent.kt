package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

/**
 * Translation memory component.
 */
interface TranslationMemoryComponent :
    MviModel<TranslationMemoryComponent.Intent, TranslationMemoryComponent.UiState, TranslationMemoryComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Clear the content of the panel.
         */
        object Clear : Intent

        /**
         * Load the TM matches for the message with a given key.
         *
         * @param key message key
         * @param projectId Project ID
         * @param languageId Language ID
         */
        data class Load(val key: String, val projectId: Int, val languageId: Int) : Intent

        /**
         * Copy the match with a given index into the translation field.
         *
         * @param index match index
         */
        data class CopyTranslation(val index: Int) : Intent
    }

    /**
     * Translation memory UI state.
     *
     * @property isLoading flag indicating whether there is a background operation in progress
     * @property units translation units to display
     * @constructor Create [UiState]
     */
    data class UiState(
        val isLoading: Boolean = false,
        val units: List<TranslationUnit> = emptyList(),
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Events triggered when a TM message should be copied into the translation editor
         */
        data class Copy(val value: String) : Effect
    }
}
