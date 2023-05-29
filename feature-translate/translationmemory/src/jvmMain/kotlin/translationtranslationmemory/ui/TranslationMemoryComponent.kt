package translationtranslationmemory.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TranslationMemoryComponent {
    val uiState: StateFlow<TranslationMemoryUiState>
    val copyEvents: SharedFlow<Int>

    fun clear()
    fun loadSimilarities(key: String, projectId: Int, languageId: Int)
    fun copyTranslation(index: Int)
}
