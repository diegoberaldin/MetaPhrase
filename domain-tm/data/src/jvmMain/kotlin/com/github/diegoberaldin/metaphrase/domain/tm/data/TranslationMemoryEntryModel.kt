package com.github.diegoberaldin.metaphrase.domain.tm.data

data class TranslationMemoryEntryModel(
    val id: Int = 0,
    val origin: String = "",
    val sourceText: String = "",
    val sourceLang: String = "",
    val targetText: String = "",
    val targetLang: String = "",
)
