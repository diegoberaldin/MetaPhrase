package panelmatches.presentation

import projectdata.TranslationUnit

data class TranslationMemoryUiState(
    val isLoading: Boolean = false,
    val units: List<TranslationUnit> = emptyList(),
)
