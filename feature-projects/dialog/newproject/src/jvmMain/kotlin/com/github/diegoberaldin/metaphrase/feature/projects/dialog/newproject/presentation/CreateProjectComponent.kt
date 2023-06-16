package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CreateProjectComponent {

    var projectId: Int
    val uiState: StateFlow<CreateProjectUiState>
    val languagesUiState: StateFlow<CreateProjectLanguagesUiState>
    val done: SharedFlow<Int?>

    fun setName(value: String)
    fun addLanguage(value: LanguageModel)
    fun setBaseLanguage(value: LanguageModel)
    fun removeLanguage(value: LanguageModel)
    fun submit()
}
