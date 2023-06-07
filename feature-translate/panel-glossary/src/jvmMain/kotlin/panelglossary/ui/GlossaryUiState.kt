package panelglossary.ui

import data.GlossaryTermModel

data class GlossaryUiState(
    val sourceFlag: String = "",
    val targetFlag: String = "",
    val isLoading: Boolean = false,
    val isBaseLanguage: Boolean = false,
    val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
)
