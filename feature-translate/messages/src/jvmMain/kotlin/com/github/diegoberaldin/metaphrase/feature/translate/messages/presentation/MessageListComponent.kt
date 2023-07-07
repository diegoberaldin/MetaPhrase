package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection
import kotlinx.coroutines.flow.StateFlow

/**
 * Message list component contract.
 */
interface MessageListComponent :
    MviModel<MessageListComponent.Intent, MessageListComponent.UiState, MessageListComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Reload the message list. It should be called for the first loading operation, for subsequent ones the
         * [Refresh] intent is enough
         *
         * @param language current language used in the search
         * @param filter message filter used in the search
         * @param projectId current project id
         */

        data class ReloadMessages(
            val language: LanguageModel,
            val filter: TranslationUnitTypeFilter,
            val projectId: Int,
        ) : Intent

        /**
         * Reload the message list.
         */
        object Refresh : Intent

        /**
         * Load the next page of messages from the DB.
         */
        object LoadNextPage : Intent

        /**
         * Search for messages matching the textual query.
         *
         * @param text text to search
         */
        data class Search(val text: String) : Intent

        /**
         * Start editing a given message.
         *
         * @param index index of the message to edit
         */
        data class StartEditing(val index: Int) : Intent

        /**
         * End the editing operation.
         */
        object EndEditing : Intent

        /**
         * Move cursor to the previous message.
         */
        object MoveToPrevious : Intent

        /**
         * Move cursor to the next message.
         */
        object MoveToNext : Intent

        /**
         * Set the current segment text (intended for user initiated interaction).
         *
         * @param text string to set
         */
        data class SetSegmentText(val text: String) : Intent

        /**
         * Change segment text programmatically (intended for application initiated interaction).
         *
         * @param text string to set
         */
        data class ChangeSegmentText(val text: String) : Intent

        /**
         * Copy the base (source) message to the target field.
         */
        object CopyBase : Intent

        /**
         * Delete the current segment.
         */
        object DeleteSegment : Intent

        /**
         * Scroll to a given message.
         *
         * @param key identifier (key) of the message
         */
        data class ScrollToMessage(val key: String) : Intent

        /**
         * Mark a segment as translatable.
         *
         * @param value translatable flag
         * @param key identifier (key) of the message
         */
        data class MarkAsTranslatable(val value: Boolean, val key: String) : Intent

        /**
         * Enable or disable editing.
         *
         * @param value flag to enable editing
         */
        data class SetEditingEnabled(val value: Boolean) : Intent

        /**
         * Clear the message list.
         */
        object ClearMessages : Intent

        /**
         * Opens the new glossary term dialog.
         *
         * @param lemma target term (prefilled in dialog)
         * @param lang language code
         */
        data class AddToGlossarySource(val lemma: String, val lang: String) : Intent

        /**
         * Ignore a given word in spelling for the current language adding it to a user defined dictionary.
         *
         * @param word word to ignore
         */
        data class IgnoreWordInSpelling(val word: String) : Intent
    }

    /**
     * Message list UI state.
     *
     * @property units list of translation units
     * @property editingIndex index of the message being edited
     * @property currentLanguage current language
     * @property editingEnabled flag indicating whether editing should be allowed
     * @property updateTextSwitch flag to trigger text updates for the current segment programmatically
     * @property canFetchMore flag indicating whether there are more messages to fetch
     * @property isLoading flag indicating whether loading is in progress
     * @property isShowingGlobalProgress flag indicating whether a global background operation is in progress
     * @property spellingErrors list of spelling error detected and the corresponding corrections
     * @constructor Create [UiState]
     */
    data class UiState(
        val units: List<TranslationUnit> = emptyList(),
        val editingIndex: Int? = null,
        val currentLanguage: LanguageModel? = null,
        val editingEnabled: Boolean = true,
        val updateTextSwitch: Boolean = false,
        val canFetchMore: Boolean = true,
        val isLoading: Boolean = false,
        val isShowingGlobalProgress: Boolean = false,
        val spellingErrors: List<SpellCheckCorrection> = emptyList(),
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Event triggered with the index of the message being edited (needed to scroll the list programmatically)
         */
        data class Selection(val index: Int) : Effect

        /**
         * Events emitted when new terms should be added to the glossary.
         */
        data class AddToGlossary(val lemma: String, val lang: String) : Effect
    }

    /**
     * Currently edited segment
     */
    val editedSegment: StateFlow<SegmentModel?>
}
