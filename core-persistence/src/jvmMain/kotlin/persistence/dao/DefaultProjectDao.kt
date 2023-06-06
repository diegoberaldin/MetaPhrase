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

internal class DefaultProjectDao : ProjectDao {
    override suspend fun create(model: ProjectModel): Int = newSuspendedTransaction {
        ProjectEntity.insertIgnore {
            it[name] = model.name
        }[ProjectEntity.id].value
    }

    override suspend fun update(model: ProjectModel) = newSuspendedTransaction {
        ProjectEntity.update({ ProjectEntity.id eq model.id }) {
            it[name] = model.name
        }
    }

    override suspend fun delete(model: ProjectModel) = newSuspendedTransaction {
        ProjectEntity.deleteWhere { id eq model.id }
    }

    override suspend fun getAll(): List<ProjectModel> = newSuspendedTransaction {
        ProjectEntity.selectAll().map { it.toModel() }
    }

    override suspend fun getById(id: Int): ProjectModel? = newSuspendedTransaction {
        ProjectEntity.select { ProjectEntity.id eq id }.firstOrNull()?.toModel()
    }

    override fun ResultRow.toModel() = ProjectModel(
        id = this[ProjectEntity.id].value,
        name = this[ProjectEntity.name],
    )
}
