package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

import kotlinx.coroutines.flow.StateFlow

interface StatisticsComponent {

    val uiState: StateFlow<StatisticsUiState>
    var projectId: Int
}
