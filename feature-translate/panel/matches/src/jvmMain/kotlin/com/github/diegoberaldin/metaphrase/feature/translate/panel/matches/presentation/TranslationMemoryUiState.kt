package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

data class TranslationMemoryUiState(
    val isLoading: Boolean = false,
    val units: List<TranslationUnit> = emptyList(),
)
