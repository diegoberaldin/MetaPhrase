package newglossaryterm.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface NewGlossaryTermComponent {
    val uiState: StateFlow<NewGlossaryTermUiState>
    val done: SharedFlow<Pair<String?, String?>>

    fun setSourceTerm(value: String)
    fun setTargetTerm(value: String)

    fun submit()
}
