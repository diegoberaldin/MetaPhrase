package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation

/**
 * Statistics row item.
 */
sealed interface StatisticsItem {
    /**
     * Divider between rows
     */
    object Divider : StatisticsItem

    /**
     * Section header.
     *
     * @property title
     * @constructor Create [Header]
     */
    data class Header(val title: String = "") : StatisticsItem

    /**
     * Language header.
     *
     * @property name language name
     * @constructor Create [LanguageHeader]
     */
    data class LanguageHeader(val name: String = "") : StatisticsItem

    /**
     * Row with a title and a value represented as a string.
     *
     * @property title row title
     * @property value statistics value
     * @constructor Create [TextRow]
     */
    data class TextRow(val title: String = "", val value: String = "") : StatisticsItem

    /**
     * Row with a title and a value represented as a bar chart.
     *
     * @property title row title
     * @property value statistics value
     * @constructor Create [BarChartRow]
     */
    data class BarChartRow(val title: String = "", val value: Float = 0f) : StatisticsItem
}

data class StatisticsUiState(val items: List<StatisticsItem> = emptyList())
