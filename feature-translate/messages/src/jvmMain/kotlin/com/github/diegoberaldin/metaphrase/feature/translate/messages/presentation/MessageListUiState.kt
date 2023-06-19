package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

data class MessageListUiState(
    val units: List<TranslationUnit> = emptyList(),
    val editingIndex: Int? = null,
    val currentLanguage: LanguageModel? = null,
    val editingEnabled: Boolean = true,
    val updateTextSwitch: Boolean = false,
)

data class MessageLisPaginationState(
    val canFetchMore: Boolean = true,
    val isLoading: Boolean = false,
)
