package com.github.diegoberaldin.metaphrase.domain.project.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Segment entity.
 */
object SegmentEntity : IntIdTable() {
    val text = largeText("text")
    val translatable = bool("translatable")
    val key = mediumText("key")
    val languageId = reference(name = "languageId", foreign = LanguageEntity, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(key, languageId)
    }
}
