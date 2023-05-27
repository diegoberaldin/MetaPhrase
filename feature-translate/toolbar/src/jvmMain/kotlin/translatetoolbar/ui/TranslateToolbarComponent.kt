package translatetoolbar.ui

import data.LanguageModel
import data.TranslationUnitTypeFilter
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TranslateToolbarComponent {

    var projectId: Int
    val uiState: StateFlow<TranslateToolbarUiState>
    val events: SharedFlow<Events>

    fun setLanguage(value: LanguageModel)
    fun setTypeFilter(value: TranslationUnitTypeFilter)
    fun onSearchFired()
    fun setSearch(value: String)
    fun copyBase()
    fun moveToPrevious()
    fun moveToNext()
    fun addUnit()
    fun removeUnit()
    fun validateUnits()
    fun setEditing(value: Boolean)

    sealed interface Events {
        object MoveToPrevious : Events
        object MoveToNext : Events
        data class Search(val text: String) : Events
        object AddUnit : Events
        object RemoveUnit : Events
        object ValidateUnits : Events
        object CopyBase : Events
    }
}
