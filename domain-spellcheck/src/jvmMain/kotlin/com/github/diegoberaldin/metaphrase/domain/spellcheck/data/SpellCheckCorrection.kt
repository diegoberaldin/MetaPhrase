package com.github.diegoberaldin.metaphrase.domain.spellcheck.data

/**
 * Spelling check correction.
 *
 * @property indices range of the error in the source message
 * @property value misspelled word
 * @property suggestions spelling suggestions
 * @constructor Create [SpellCheckCorrection]
 */
data class SpellCheckCorrection(
    val indices: IntRange = IntRange.EMPTY,
    val value: String = "",
    val suggestions: List<String> = emptyList(),
)
