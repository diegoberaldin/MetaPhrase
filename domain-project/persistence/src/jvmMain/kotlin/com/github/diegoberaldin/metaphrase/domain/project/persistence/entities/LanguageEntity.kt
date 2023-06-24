package com.github.diegoberaldin.metaphrase.domain.project.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Language entity. This belongs to this subproject because of its reference to the project entity.
 */
object LanguageEntity : IntIdTable() {
    val code = varchar("code", 2)
    val projectId = reference(name = "projectId", foreign = ProjectEntity, onDelete = ReferenceOption.CASCADE)
    val isBase = bool("isBase")

    init {
        uniqueIndex(code, projectId)
    }
}
