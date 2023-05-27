package projectstatistics.ui

import kotlinx.coroutines.flow.StateFlow

interface StatisticsComponent {

    val uiState: StateFlow<StatisticsUiState>
    var projectId: Int
}
