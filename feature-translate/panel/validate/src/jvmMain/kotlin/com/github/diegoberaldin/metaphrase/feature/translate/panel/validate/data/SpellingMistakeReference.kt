package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data

/**
 * Spelling mistake reference.
 *
 * @property key message key
 * @property mistakes list of incorrect words
 * @constructor Create [SpellingMistakeReference]
 */
data class SpellingMistakeReference(
    val key: String,
    val mistakes: List<String>,
)
