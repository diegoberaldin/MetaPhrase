package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TranslationMemoryComponent {
    val uiState: StateFlow<TranslationMemoryUiState>
    val copyEvents: SharedFlow<String>

    fun clear()
    fun load(key: String, projectId: Int, languageId: Int)
    fun copyTranslation(index: Int)
}
