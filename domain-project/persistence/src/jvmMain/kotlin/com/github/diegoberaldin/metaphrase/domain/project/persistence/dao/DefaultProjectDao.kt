package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.ProjectEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class DefaultProjectDao : ProjectDao {
    override suspend fun create(model: ProjectModel): Int = newSuspendedTransaction {
        ProjectEntity.insertIgnore {
            it[name] = model.name
        }[ProjectEntity.id].value
    }

    override suspend fun update(model: ProjectModel): Unit = newSuspendedTransaction {
        ProjectEntity.update({ ProjectEntity.id eq model.id }) {
            it[name] = model.name
        }
    }

    override suspend fun delete(model: ProjectModel): Unit = newSuspendedTransaction {
        ProjectEntity.deleteWhere { id eq model.id }
    }

    override suspend fun deleteAll(): Unit = newSuspendedTransaction {
        ProjectEntity.deleteAll()
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
