package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

data class GlossaryUiState(
    val sourceFlag: String = "",
    val targetFlag: String = "",
    val isLoading: Boolean = false,
    val isBaseLanguage: Boolean = false,
    val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
)
