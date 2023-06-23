package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

/**
 * UI state for the translation editor.
 *
 * @property project current project
 * @property unitCount number of translation units
 * @property needsSaving whether the project needs saving
 * @constructor Create [TranslateUiState]
 */
data class TranslateUiState(
    val project: ProjectModel? = null,
    val unitCount: Int = 0,
    val needsSaving: Boolean = false,
)
