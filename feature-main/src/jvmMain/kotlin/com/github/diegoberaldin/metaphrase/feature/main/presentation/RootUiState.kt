package com.github.diegoberaldin.metaphrase.feature.main.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

/**
 * Root UI state.
 *
 * @property activeProject project currently opened
 * @property isEditing flag indicating whether any message is being edited
 * @property currentLanguage current language selected in the toolbar
 * @property isLoading flag indicating whether there is an operation running in the background
 * @property isSaveEnabled flag indicating whether the save menu action should be enabled
 * @constructor Create [RootUiState]
 */
data class RootUiState(
    val activeProject: ProjectModel? = null,
    val isEditing: Boolean = false,
    val currentLanguage: LanguageModel? = null,
    val isLoading: Boolean = false,
    val isSaveEnabled: Boolean = false,
)
