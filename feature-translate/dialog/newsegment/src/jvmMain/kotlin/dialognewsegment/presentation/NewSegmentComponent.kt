package dialognewsegment.presentation

import projectdata.LanguageModel
import projectdata.SegmentModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface NewSegmentComponent {

    val uiState: StateFlow<NewSegmentUiState>
    val done: SharedFlow<SegmentModel?>
    var projectId: Int
    var language: LanguageModel

    fun setKey(value: String)
    fun setText(value: String)
    fun close()
    fun submit()
}
