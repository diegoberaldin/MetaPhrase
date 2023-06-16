package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TranslateToolbarComponent {

    var projectId: Int
    val uiState: StateFlow<TranslateToolbarUiState>
    val events: SharedFlow<Events>
    val currentLanguage: StateFlow<LanguageModel?>

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
