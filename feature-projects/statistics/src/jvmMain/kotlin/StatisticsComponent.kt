import com.arkivanov.decompose.ComponentContext
import common.utils.getByInjection
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface StatisticsComponent {

    val uiState: StateFlow<StatisticsUiState>
    var projectId: Int

    companion object {
        fun newInstance(
            componentContext: ComponentContext,
            coroutineContext: CoroutineContext,
        ) = DefaultStatisticsComponent(
            componentContext = componentContext,
            coroutineContext = coroutineContext,
            dispatchers = getByInjection(),
            languageRepository = getByInjection(),
            segmentRepository = getByInjection(),
            completeLanguage = getByInjection(),
        )
    }
}
