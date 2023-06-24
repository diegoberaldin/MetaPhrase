package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Translation memory component.
 */
interface TranslationMemoryComponent {
    /**
     * UI state
     */
    val uiState: StateFlow<TranslationMemoryUiState>

    /**
     * Events triggered when a TM message should be copied into the translation editor
     */
    val copyEvents: SharedFlow<String>

    /**
     * Clear the content of the panel.
     */
    fun clear()

    /**
     * Load the TM matches for the message with a given key.
     *
     * @param key message key
     * @param projectId Project ID
     * @param languageId Language ID
     */
    fun load(key: String, projectId: Int, languageId: Int)

    /**
     * Copy the match with a given index into the translation field.
     *
     * @param index match index
     */
    fun copyTranslation(index: Int)
}
