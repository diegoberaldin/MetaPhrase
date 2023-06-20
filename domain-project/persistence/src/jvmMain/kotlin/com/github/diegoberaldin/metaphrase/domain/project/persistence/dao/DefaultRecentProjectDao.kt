package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.RecentProjectEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DefaultRecentProjectDao : RecentProjectDao {
    override suspend fun getAll(): List<RecentProjectModel> = newSuspendedTransaction {
        RecentProjectEntity.selectAll().map { it.toModel() }
    }

    override suspend fun getByName(value: String): RecentProjectModel? = newSuspendedTransaction {
        RecentProjectEntity.select { RecentProjectEntity.name eq value }.firstOrNull()?.toModel()
    }

    override suspend fun delete(model: RecentProjectModel): Unit = newSuspendedTransaction {
        RecentProjectEntity.deleteWhere { RecentProjectEntity.id eq model.id }
    }

    override suspend fun create(model: RecentProjectModel): Int = newSuspendedTransaction {
        RecentProjectEntity.insertIgnore {
            it[path] = model.path
            it[name] = model.name
        }[RecentProjectEntity.id].value
    }

    private fun ResultRow.toModel(): RecentProjectModel = RecentProjectModel(
        id = this[RecentProjectEntity.id].value,
        path = this[RecentProjectEntity.path],
        name = this[RecentProjectEntity.name],
    )
}
