package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

/**
 * Message list UI state.
 *
 * @property units list of translation units
 * @property editingIndex index of the message being edited
 * @property currentLanguage current language
 * @property editingEnabled flag indicating whether editing should be allowed
 * @property updateTextSwitch flag to trigger text updates for the current segment programmatically
 * @property canFetchMore flag indicating whether there are more messages to fetch
 * @property isLoading flag indicating whether loading is in progress
 * @constructor Create [MessageListUiState]
 */
data class MessageListUiState(
    val units: List<TranslationUnit> = emptyList(),
    val editingIndex: Int? = null,
    val currentLanguage: LanguageModel? = null,
    val editingEnabled: Boolean = true,
    val updateTextSwitch: Boolean = false,
    val canFetchMore: Boolean = true,
    val isLoading: Boolean = false,
)
