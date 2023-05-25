package persistence.dao

import data.LanguageModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import persistence.entities.LanguageEntity
import persistence.entities.LanguageEntity.code
import persistence.entities.LanguageEntity.id
import persistence.entities.LanguageEntity.isBase

class LanguageDao {
    suspend fun create(model: LanguageModel, projectId: Int): Int = newSuspendedTransaction {
        LanguageEntity.insertIgnore {
            it[code] = model.code
            it[isBase] = model.isBase
            it[LanguageEntity.projectId] = projectId
        }[LanguageEntity.id].value
    }

    suspend fun update(model: LanguageModel) = newSuspendedTransaction {
        LanguageEntity.update({ LanguageEntity.id eq model.id }) {
            it[isBase] = model.isBase
        }
    }

    suspend fun delete(model: LanguageModel) = newSuspendedTransaction {
        LanguageEntity.deleteWhere { LanguageEntity.id eq model.id }
    }

    suspend fun getBase(projectId: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { (LanguageEntity.projectId eq projectId) and (isBase eq true) }.firstOrNull()?.toModel()
    }

    suspend fun getAll(projectId: Int): List<LanguageModel> = newSuspendedTransaction {
        LanguageEntity.select { LanguageEntity.projectId eq projectId }.map { it.toModel() }
    }

    suspend fun getByCode(code: String, projectId: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { (LanguageEntity.code eq code) and (LanguageEntity.projectId eq projectId) }
            .firstOrNull()?.toModel()
    }

    suspend fun getById(id: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { LanguageEntity.id eq id }
            .firstOrNull()?.toModel()
    }

    private fun ResultRow.toModel() = LanguageModel(
        id = this[id].value,
        code = this[code],
        isBase = this[isBase],
    )
}
