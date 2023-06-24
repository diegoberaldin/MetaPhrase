package com.github.diegoberaldin.metaphrase.domain.glossary.data

/**
 * Glossary term model.
 *
 * @property id Term ID
 * @property lemma Term lemma
 * @property lang Language code
 * @constructor Create [GlossaryTermModel]
 */
data class GlossaryTermModel(
    val id: Int = 0,
    val lemma: String = "",
    val lang: String = "",
)
