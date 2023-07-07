package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data

/**
 * Available validation panel content.
 */
sealed interface ValidationContent {
    /**
     * Placeholders validation content.
     *
     * @property references list of references to messages containing invalid placeholders
     * @constructor Create [InvalidPlaceholders]
     */
    data class InvalidPlaceholders(val references: List<InvalidPlaceholderReference> = emptyList()) : ValidationContent

    /**
     * Spelling mistakes content.
     *
     * @property references list of references to messages containing spelling mistakes
     * @constructor Create [SpellingMistakes]
     */
    data class SpellingMistakes(val references: List<SpellingMistakeReference> = emptyList()) : ValidationContent
}
