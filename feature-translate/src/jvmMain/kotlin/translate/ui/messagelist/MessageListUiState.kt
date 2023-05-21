package translate.ui.messagelist

import data.SegmentModel

data class TranslationUnit(
    val segment: SegmentModel,
    val original: SegmentModel? = null,
)

data class MessageListUiState(
    val units: List<TranslationUnit> = emptyList(),
    val editingIndex: Int? = null,
    val isBaseLanguage: Boolean = false,
)
