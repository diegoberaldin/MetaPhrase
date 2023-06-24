package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Machine translation component.
 */
interface MachineTranslationComponent {
    /**
     * UI state
     */
    val uiState: StateFlow<MachineTranslationUiState>

    /**
     * Events triggered when the content of the suggestion field from MT needs to be copied into the translation editor.
     */
    val copySourceEvents: SharedFlow<String>

    /**
     * Events triggered when the content of the translation editor needs to be copied to the suggestion field.
     */
    val copyTargetEvents: SharedFlow<Unit>

    /**
     * Events triggered after a message share with the MT provider (true if successful, false otherwise)
     */
    val shareEvents: SharedFlow<Boolean>

    /**
     * Clear the content of the panel.
     */
    fun clear()

    /**
     * Load the data for the message with a given key. No suggestion is retrieved until the [retrieve] method is called.
     * This is intended to reduce the request number and not exceed the service quota.
     *
     * @param key message key
     * @param projectId Project ID
     * @param languageId Language ID
     */
    fun load(key: String, projectId: Int, languageId: Int)

    /**
     * Retrieve a suggestion from the MT provider. The [load] method should be called to set the language and the source
     * message that will be translated.
     */
    fun retrieve()

    /**
     * Signal the user intention to copy the suggestion into the editor, triggering a [copySourceEvents] event.
     */
    fun insertTranslation()

    /**
     * Signal the user intention to copy the content of the target field in the translation editor into the suggestion
     * field, triggering a [copyTargetEvents] event.
     */
    fun copyTarget()

    /**
     * Set a value for the suggestion, when the event is initiated by the user.
     *
     * @param value suggestion to set
     */
    fun setTranslation(value: String)

    /**
     * Programmatically update the value of the suggestion.
     *
     * @param value suggestion to set
     */
    fun copyTranslation(value: String)

    /**
     * Share the suggestion field content with the remote MT provider.
     */
    fun share()
}
