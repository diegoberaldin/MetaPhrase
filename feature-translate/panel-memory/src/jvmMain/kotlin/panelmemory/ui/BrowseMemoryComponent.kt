package panelmemory.ui

import data.LanguageModel
import kotlinx.coroutines.flow.StateFlow

interface BrowseMemoryComponent {
    val uiState: StateFlow<BrowseMemoryUiState>

    fun setLanguages(source: LanguageModel? = null, target: LanguageModel? = null)
    fun setSourceLanguage(value: LanguageModel?)
    fun setTargetLanguage(value: LanguageModel?)
    fun setSearch(value: String)
    fun onSearchFired()
    fun deleteEntry(index: Int)
}
