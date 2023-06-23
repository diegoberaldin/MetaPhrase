package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

/**
 * New glossary term UI state.
 *
 * @property sourceTerm source term
 * @property sourceTermError error for the source term field
 * @property targetTerm target get
 * @property targetTermError error for the target term field
 * @constructor Create [NewGlossaryTermUiState]
 */
data class NewGlossaryTermUiState(
    val sourceTerm: String = "",
    val sourceTermError: String = "",
    val targetTerm: String = "",
    val targetTermError: String = "",
)
