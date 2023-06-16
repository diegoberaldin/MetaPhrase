package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation

import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.InvalidPlaceholderReference
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.SpellingMistakeReference

data class InvalidSegmentUiState(
    val content: ValidationContent? = null,
)

sealed interface ValidationContent {
    data class InvalidPlaceholders(val references: List<InvalidPlaceholderReference> = emptyList()) : ValidationContent
    data class SpellingMistakes(val references: List<SpellingMistakeReference> = emptyList()) : ValidationContent
}
