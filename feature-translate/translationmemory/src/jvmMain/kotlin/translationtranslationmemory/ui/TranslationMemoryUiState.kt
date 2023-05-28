package translationtranslationmemory.ui

import data.TranslationUnit

data class TranslationMemoryUiState(
    val isLoading: Boolean = false,
    val units: List<TranslationUnit> = emptyList(),
)
