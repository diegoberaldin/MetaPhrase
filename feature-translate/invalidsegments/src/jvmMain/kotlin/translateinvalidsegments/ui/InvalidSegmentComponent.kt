package translateinvalidsegments.ui

import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface InvalidSegmentComponent {

    val uiState: StateFlow<InvalidSegmentUiState>
    var projectId: Int
    var languageId: Int
    var invalidKeys: List<String>
    val selectionEvents: SharedFlow<String>
    fun setCurrentIndex(value: Int)

    object Factory {
        fun create(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ) = DefaultInvalidSegmentComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            languageRepository = getByInjection(),
            segmentRepository = getByInjection(),
        )
    }
}
