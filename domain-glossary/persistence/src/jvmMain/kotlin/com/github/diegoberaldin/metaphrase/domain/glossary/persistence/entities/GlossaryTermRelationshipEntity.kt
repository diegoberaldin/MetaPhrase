package com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Association table for the M:N relationship between terms.
 */
object GlossaryTermRelationshipEntity : IntIdTable() {
    val id1 = reference("id1", foreign = GlossaryTermEntity, onDelete = ReferenceOption.CASCADE)
    val id2 = reference("id2", foreign = GlossaryTermEntity, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(id1, id2)
    }
}
