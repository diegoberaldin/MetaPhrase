package translatemessages.ui

import data.LanguageModel
import data.SegmentModel

data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
)

data class MessageListUiState(
    val units: List<TranslationUnit> = emptyList(),
    val editingIndex: Int? = null,
    val currentLanguage: LanguageModel? = null,
    val editingEnabled: Boolean = true,
)
