package com.github.diegoberaldin.metaphrase.domain.project.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object RecentProjectEntity : IntIdTable() {
    val path = mediumText("path")
}
