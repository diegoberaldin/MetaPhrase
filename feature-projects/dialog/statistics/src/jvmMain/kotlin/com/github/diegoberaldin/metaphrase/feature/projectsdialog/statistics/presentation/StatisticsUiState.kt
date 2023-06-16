package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

sealed interface StatisticsItem {
    object Divider : StatisticsItem
    data class Header(val title: String = "") : StatisticsItem
    data class LanguageHeader(val name: String = "") : StatisticsItem
    data class TextRow(val title: String = "", val value: String = "") : StatisticsItem
    data class BarChartRow(val title: String = "", val value: Float = 0f) : StatisticsItem
}

data class StatisticsUiState(val items: List<StatisticsItem> = emptyList())
