package translationtranslationmemory.ui

import data.TranslationUnit

data class TranslationMemoryUiState(
    val loading: Boolean = false,
    val units: List<TranslationUnit> = emptyList(),
)
