package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MachineTranslationComponent {
    val uiState: StateFlow<MachineTranslationUiState>
    val copySourceEvents: SharedFlow<String>
    val copyTargetEvents: SharedFlow<Unit>
    val shareEvents: SharedFlow<Boolean>

    fun clear()
    fun load(key: String, projectId: Int, languageId: Int)
    fun retrieve()
    fun insertTranslation()
    fun copyTarget()
    fun setTranslation(value: String)
    fun copyTranslation(value: String)
    fun share()
}
