package panelglossary.ui

import data.GlossaryTermModel

data class GlossaryUiState(
    val loading: Boolean = false,
    val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
)
