package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Add to glossary event.
 *
 * @property lemma lemma to add
 * @property lang language code
 * @constructor Create [AddToGlossaryEvent]
 */
data class AddToGlossaryEvent(val lemma: String, val lang: String)

/**
 * Message list component.
 */
interface MessageListComponent {

    /**
     * UI state
     */
    val uiState: StateFlow<MessageListUiState>

    /**
     * Event triggered with the index of the message being edited (needed to scroll the list programmatically)
     */
    val selectionEvents: SharedFlow<Int>

    /**
     * Currently edited segment
     */
    val editedSegment: StateFlow<SegmentModel?>

    /**
     * Flag indicating whether a background operation is in progress
     */
    val isShowingProgress: StateFlow<Boolean>

    /**
     * List of spelling error detected and the corresponding corrections
     */
    val spellingErrors: StateFlow<List<SpellCheckCorrection>>

    /**
     * Events emitted when new terms should be added to the glossary (see [AddToGlossaryEvent])
     */
    val addToGlossaryEvents: SharedFlow<AddToGlossaryEvent>

    /**
     * Reload the message list. It should be called for the first loading operation, for subsequent ones the
     * [refresh] method is enough
     *
     * @param language current language used in the search
     * @param filter message filter used in the search
     * @param projectId current project id
     */
    fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int)

    /**
     * Reload the message list.
     */
    fun refresh()

    /**
     * Load the next page of messages from the DB.
     */
    fun loadNextPage()

    /**
     * Search for messages matching the textual query.
     *
     * @param text text to search
     */
    fun search(text: String)

    /**
     * Start editing a given message.
     *
     * @param index index of the message to edit
     */
    fun startEditing(index: Int)

    /**
     * End the editing operation.
     */
    fun endEditing()

    /**
     * Move cursor to the previous message.
     */
    fun moveToPrevious()

    /**
     * Move cursor to the next message.
     */
    fun moveToNext()

    /**
     * Set the current segment text (intended for user initiated interaction).
     *
     * @param text string to set
     */
    fun setSegmentText(text: String)

    /**
     * Change segment text programmatically (intended for application initiated interaction).
     *
     * @param text string to set
     */
    fun changeSegmentText(text: String)

    /**
     * Copy the base (source) message to the target field.
     */
    fun copyBase()

    /**
     * Delete the current segment.
     */
    fun deleteSegment()

    /**
     * Scroll to a given message.
     *
     * @param key identifier (key) of the message
     */
    fun scrollToMessage(key: String)

    /**
     * Mark a segment as translatable.
     *
     * @param value translatable flag
     * @param key identifier (key) of the message
     */
    fun markAsTranslatable(value: Boolean, key: String)

    /**
     * Enable or disable editing.
     *
     * @param value flag to enable editing
     */
    fun setEditingEnabled(value: Boolean)

    /**
     * Clear the message list.
     */
    fun clearMessages()

    /**
     * Opens the new glossary term dialog.
     *
     * @param lemma target term (prefilled in dialog)
     * @param lang language code
     */
    fun addToGlossarySource(lemma: String, lang: String)

    /**
     * Ignore a given word in spelling for the current language adding it to a user defined dictionary.
     *
     * @param word word to ignore
     */
    fun ignoreWordInSpelling(word: String)
}
