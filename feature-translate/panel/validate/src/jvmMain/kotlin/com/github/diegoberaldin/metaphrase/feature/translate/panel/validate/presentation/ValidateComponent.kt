package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Validation component.
 */
interface ValidateComponent {

    /**
     * UI state
     */
    val uiState: StateFlow<InvalidSegmentUiState>

    /**
     * Events triggered when a reference to a message is selected with the key of the message
     */
    val selectionEvents: SharedFlow<String>

    /**
     * Load a list of invalid placeholder references.
     *
     * @param projectId Project ID
     * @param languageId Language ID
     * @param invalidKeys Invalid message keys
     */
    fun loadInvalidPlaceholders(projectId: Int, languageId: Int, invalidKeys: List<String>)

    /**
     * Load a list of spelling mistake references.
     *
     * @param errors list of spelling errors (message key to list of incorrect words)
     */
    fun loadSpellingMistakes(errors: Map<String, List<String>>)

    /**
     * Clear the panel content.
     */
    fun clear()

    /**
     * Select an reference, triggering [selectionEvents].
     *
     * @param value index of the reference to select
     */
    fun selectItem(value: Int)
}
