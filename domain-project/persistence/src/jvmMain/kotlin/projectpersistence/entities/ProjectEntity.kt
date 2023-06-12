package projectpersistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object ProjectEntity : IntIdTable() {
    val name = mediumText("name")
}
