package panelglossary.presentation

import glossarydata.GlossaryTermModel
import kotlinx.coroutines.flow.StateFlow

interface GlossaryComponent {
    val uiState: StateFlow<GlossaryUiState>

    fun clear()
    fun loadGlossaryTerms(key: String, projectId: Int, languageId: Int)
    fun addSourceTerm(lemma: String)
    fun addTargetTerm(lemma: String, source: GlossaryTermModel)
    fun deleteTerm(term: GlossaryTermModel)
}
