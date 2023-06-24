package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

/**
 * Translation memory UI state.
 *
 * @property isLoading flag indicating whether there is a background operation in progress
 * @property units translation units to display
 * @constructor Create [TranslationMemoryUiState]
 */
data class TranslationMemoryUiState(
    val isLoading: Boolean = false,
    val units: List<TranslationUnit> = emptyList(),
)
