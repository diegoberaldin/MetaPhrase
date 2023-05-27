package translateinvalidsegments.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InvalidSegmentComponent {

    val uiState: StateFlow<InvalidSegmentUiState>
    var projectId: Int
    var languageId: Int
    var invalidKeys: List<String>
    val selectionEvents: SharedFlow<String>
    fun setCurrentIndex(value: Int)
}
