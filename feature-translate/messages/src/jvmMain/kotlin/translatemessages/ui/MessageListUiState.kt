package translatemessages.ui

import data.LanguageModel
import data.TranslationUnit

data class MessageListUiState(
    val units: List<TranslationUnit> = emptyList(),
    val editingIndex: Int? = null,
    val currentLanguage: LanguageModel? = null,
    val editingEnabled: Boolean = true,
)
