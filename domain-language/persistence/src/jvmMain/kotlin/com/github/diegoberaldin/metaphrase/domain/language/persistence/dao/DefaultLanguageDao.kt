package com.github.diegoberaldin.metaphrase.domain.language.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.LanguageEntity
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class DefaultLanguageDao : LanguageDao {
    override suspend fun create(model: LanguageModel, projectId: Int): Int = newSuspendedTransaction {
        LanguageEntity.insertIgnore {
            it[code] = model.code
            it[isBase] = model.isBase
            it[LanguageEntity.projectId] = projectId
        }[LanguageEntity.id].value
    }

    override suspend fun update(model: LanguageModel) = newSuspendedTransaction {
        LanguageEntity.update({ LanguageEntity.id eq model.id }) {
            it[isBase] = model.isBase
        }
    }

    override suspend fun delete(model: LanguageModel) = newSuspendedTransaction {
        LanguageEntity.deleteWhere { LanguageEntity.id eq model.id }
    }

    override suspend fun getBase(projectId: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { (LanguageEntity.projectId eq projectId) and (LanguageEntity.isBase eq true) }
            .firstOrNull()?.toModel()
    }

    override suspend fun getAll(projectId: Int): List<LanguageModel> = newSuspendedTransaction {
        LanguageEntity.select { LanguageEntity.projectId eq projectId }.map { it.toModel() }
    }

    override suspend fun getByCode(code: String, projectId: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { (LanguageEntity.code eq code) and (LanguageEntity.projectId eq projectId) }
            .firstOrNull()?.toModel()
    }

    override suspend fun getById(id: Int): LanguageModel? = newSuspendedTransaction {
        LanguageEntity.select { LanguageEntity.id eq id }
            .firstOrNull()?.toModel()
    }

    override fun ResultRow.toModel() = LanguageModel(
        id = this[LanguageEntity.id].value,
        code = this[LanguageEntity.code],
        isBase = this[LanguageEntity.isBase],
    )
}
