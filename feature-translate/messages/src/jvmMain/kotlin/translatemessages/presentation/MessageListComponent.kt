package translatemessages.presentation

import data.LanguageModel
import data.SegmentModel
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import spellcheck.SpellCheckCorrection

interface MessageListComponent {

    val uiState: StateFlow<MessageListUiState>
    val selectionEvents: SharedFlow<Int>
    val editedSegment: StateFlow<SegmentModel?>
    val spellingErrors: StateFlow<List<SpellCheckCorrection>>
    val paginationState: StateFlow<MessageLisPaginationState>
    val addToGlossaryEvents: SharedFlow<Pair<String, String>>

    fun reloadMessages(language: LanguageModel, filter: TranslationUnitTypeFilter, projectId: Int)
    fun refresh()
    fun loadNextPage()
    fun search(text: String)
    fun startEditing(index: Int)
    fun endEditing()
    fun moveToPrevious()
    fun moveToNext()
    fun setSegmentText(text: String)
    fun changeSegmentText(text: String)
    fun copyBase()
    fun deleteSegment()
    fun scrollToMessage(key: String)
    fun markAsTranslatable(value: Boolean, key: String)
    fun setEditingEnabled(value: Boolean)
    fun clearMessages()
    fun addToGlossarySource(lemma: String, lang: String)
}