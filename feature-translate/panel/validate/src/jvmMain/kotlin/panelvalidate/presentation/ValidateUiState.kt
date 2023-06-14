package panelvalidate.presentation

import panelvalidate.data.InvalidPlaceholderReference
import panelvalidate.data.SpellingMistakeReference

data class InvalidSegmentUiState(
    val content: ValidationContent? = null,
)

sealed interface ValidationContent {
    data class InvalidPlaceholders(val references: List<InvalidPlaceholderReference> = emptyList()) : ValidationContent
    data class SpellingMistakes(val references: List<SpellingMistakeReference> = emptyList()) : ValidationContent
}