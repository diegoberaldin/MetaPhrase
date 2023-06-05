package panelvalidate.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface InvalidSegmentComponent {

    val uiState: StateFlow<InvalidSegmentUiState>
    val selectionEvents: SharedFlow<String>

    fun load(projectId: Int, languageId: Int, invalidKeys: List<String>)
    fun clear()
    fun setCurrentIndex(value: Int)
}
