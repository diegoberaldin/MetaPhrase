package com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object GlossaryTermEntity : IntIdTable() {
    val lang = varchar("lang", 2)
    val lemma = mediumText("lemma")

    init {
        uniqueIndex(lang, lemma)
    }
}
