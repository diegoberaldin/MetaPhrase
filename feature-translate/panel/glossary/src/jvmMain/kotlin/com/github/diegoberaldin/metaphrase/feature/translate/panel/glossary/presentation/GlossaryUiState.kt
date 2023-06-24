package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

/**
 * Glossary panel UI state.
 *
 * @property sourceFlag flag of the source language
 * @property targetFlag flag of the target language
 * @property isLoading indicating whether there is a background operation in progress
 * @property isBaseLanguage indicating whether this is the base (deprecated) glossary panel, now it is displayed only for target languages
 * @property terms terms to show (1 source term for _n_ target terms)
 * @constructor Create [GlossaryUiState]
 */
data class GlossaryUiState(
    val sourceFlag: String = "",
    val targetFlag: String = "",
    val isLoading: Boolean = false,
    val isBaseLanguage: Boolean = false,
    val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
)
