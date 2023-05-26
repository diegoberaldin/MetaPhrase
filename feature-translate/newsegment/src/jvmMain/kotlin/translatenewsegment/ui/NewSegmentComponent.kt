package translatenewsegment.ui

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import data.LanguageModel
import data.SegmentModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface NewSegmentComponent {

    val uiState: StateFlow<NewSegmentUiState>
    val done: SharedFlow<SegmentModel?>
    var projectId: Int
    var language: LanguageModel

    fun setKey(value: String)
    fun setText(value: String)
    fun close()
    fun submit()

    companion object {
        fun newInstance(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ): NewSegmentComponent = DefaultNewSegmentComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            languageRepository = getByInjection(),
            segmentRepository = getByInjection(),
        )
    }
}
