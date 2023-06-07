package panelglossary.ui

import kotlinx.coroutines.flow.StateFlow

interface GlossaryComponent {
    val uiState: StateFlow<GlossaryUiState>

    fun clear()
    fun loadGlossaryTerms(key: String, projectId: Int, languageId: Int)
    fun addSourceTerm(lemma: String)
}
