package translationtranslationmemory.ui

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TranslationMemoryComponent {
    var projectId: Int
    var languageId: Int
    val uiState: StateFlow<TranslationMemoryUiState>
    val copyEvents: SharedFlow<Int>

    fun loadSimilarities(key: String)
    fun copyTranslation(index: Int)
}
