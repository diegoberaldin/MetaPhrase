package persistence.dao

import data.ProjectModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import persistence.entities.ProjectEntity

class ProjectDao {
    suspend fun create(model: ProjectModel): Int = newSuspendedTransaction {
        ProjectEntity.insertIgnore {
            it[name] = model.name
        }[ProjectEntity.id].value
    }

    suspend fun update(model: ProjectModel) = newSuspendedTransaction {
        ProjectEntity.update({ ProjectEntity.id eq model.id }) {
            it[name] = model.name
        }
    }

    suspend fun delete(model: ProjectModel) = newSuspendedTransaction {
        ProjectEntity.deleteWhere { id eq model.id }
    }

    suspend fun getAll(): List<ProjectModel> = newSuspendedTransaction {
        ProjectEntity.selectAll().map { it.toModel() }
    }

    suspend fun getById(id: Int): ProjectModel? = newSuspendedTransaction {
        ProjectEntity.select { ProjectEntity.id eq id }.firstOrNull()?.toModel()
    }

    private fun ResultRow.toModel() = ProjectModel(
        id = this[ProjectEntity.id].value,
        name = this[ProjectEntity.name],
    )
}
