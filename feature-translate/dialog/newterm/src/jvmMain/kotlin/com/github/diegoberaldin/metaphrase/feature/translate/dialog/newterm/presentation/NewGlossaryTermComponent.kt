package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class GlossaryTermPair(
    val sourceLemma: String,
    val targetLemma: String,
)

interface NewGlossaryTermComponent {
    val uiState: StateFlow<NewGlossaryTermUiState>
    val done: SharedFlow<GlossaryTermPair>

    fun setSourceTerm(value: String)
    fun setTargetTerm(value: String)

    fun submit()
}
