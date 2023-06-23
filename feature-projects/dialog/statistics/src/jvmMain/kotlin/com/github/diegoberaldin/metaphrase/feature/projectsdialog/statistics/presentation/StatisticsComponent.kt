package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

import kotlinx.coroutines.flow.StateFlow

/**
 * Statistics component.
 */
interface StatisticsComponent {
    /**
     * UI state.
     */
    val uiState: StateFlow<StatisticsUiState>

    /**
     * Project ID.
     */
    var projectId: Int
}
