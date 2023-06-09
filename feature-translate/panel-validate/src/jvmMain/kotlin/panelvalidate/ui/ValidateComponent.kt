package panelvalidate.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ValidateComponent {

    val uiState: StateFlow<InvalidSegmentUiState>
    val selectionEvents: SharedFlow<String>

    fun loadInvalidPlaceholders(projectId: Int, languageId: Int, invalidKeys: List<String>)
    fun clear()
    fun selectItem(value: Int)
}
