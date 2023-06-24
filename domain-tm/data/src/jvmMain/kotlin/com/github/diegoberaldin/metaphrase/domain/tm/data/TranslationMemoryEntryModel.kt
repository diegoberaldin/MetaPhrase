package com.github.diegoberaldin.metaphrase.domain.tm.data

/**
 * Translation memory entry model.
 *
 * @property id entry ID
 * @property identifier message key or translation unit ID (tuid)
 * @property origin name of the source or project the message comes from
 * @property sourceText source text
 * @property sourceLang source language code
 * @property targetText target text
 * @property targetLang target language code
 * @constructor Create [TranslationMemoryEntryModel]
 */
data class TranslationMemoryEntryModel(
    val id: Int = 0,
    val identifier: String = "",
    val origin: String = "",
    val sourceText: String = "",
    val sourceLang: String = "",
    val targetText: String = "",
    val targetLang: String = "",
)
