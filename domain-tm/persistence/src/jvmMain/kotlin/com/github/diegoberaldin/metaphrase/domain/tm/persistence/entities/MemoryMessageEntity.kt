package com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * TM message entity.
 */
object MemoryMessageEntity : IntIdTable() {
    /**
     * Message content
     */
    val text = largeText("text")

    /**
     * Language code
     */
    val lang = varchar("lang", 2)

    /**
     * ID of the associated translation memory entry
     */
    val entryId = reference("entryId", foreign = MemoryEntryEntity, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(lang, entryId)
    }
}
