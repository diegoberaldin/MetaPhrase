package com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object MemoryEntryEntity : IntIdTable() {
    val origin = mediumText("origin")
}
