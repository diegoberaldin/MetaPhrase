package com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object MemoryMessageEntity : IntIdTable() {
    val text = largeText("text")
    val lang = varchar("lang", 2)
    val entryId = reference("entryId", foreign = MemoryEntryEntity, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(lang, entryId)
    }
}
