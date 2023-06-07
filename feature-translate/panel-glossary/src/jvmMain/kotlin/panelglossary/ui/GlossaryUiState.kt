package panelglossary.ui

import data.GlossaryTermModel

data class GlossaryUiState(
    val isLoading: Boolean = false,
    val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
)
