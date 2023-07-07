package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * Statistics component.
 */
interface StatisticsComponent :
    MviModel<StatisticsComponent.Intent, StatisticsComponent.UiState, StatisticsComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent

    /**
     * Effects.
     */

    sealed interface Effect

    /**
     * UI state.
     *
     * @property items
     * @constructor Create [UiState]
     */
    data class UiState(val items: List<StatisticsItem> = emptyList())

    /**
     * Project ID.
     */
    var projectId: Int
}
