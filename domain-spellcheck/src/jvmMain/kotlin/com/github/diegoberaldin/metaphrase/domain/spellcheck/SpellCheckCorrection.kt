package com.github.diegoberaldin.metaphrase.domain.spellcheck

data class SpellCheckCorrection(
    val indices: IntRange = IntRange.EMPTY,
    val value: String = "",
    val suggestions: List<String> = emptyList(),
)
