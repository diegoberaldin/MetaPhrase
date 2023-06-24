package com.github.diegoberaldin.metaphrase.domain.project.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Project entity.
 */
object ProjectEntity : IntIdTable() {
    val name = mediumText("name")
}
