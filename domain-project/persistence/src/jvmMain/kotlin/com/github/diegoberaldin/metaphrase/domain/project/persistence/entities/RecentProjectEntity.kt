package com.github.diegoberaldin.metaphrase.domain.project.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Recent project entity.
 */
object RecentProjectEntity : IntIdTable() {
    val name = mediumText("name")
    val path = mediumText("path")

    init {
        uniqueIndex(name)
    }
}
