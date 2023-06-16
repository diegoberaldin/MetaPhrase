package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

data class NewGlossaryTermUiState(
    val sourceTerm: String = "",
    val sourceTermError: String = "",
    val targetTerm: String = "",
    val targetTermError: String = "",
)
