package persistence.dao

import data.SegmentModel
import data.TranslationUnitTypeFilter
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import persistence.entities.SegmentEntity
import persistence.entities.SegmentEntity.id
import persistence.entities.SegmentEntity.key
import persistence.entities.SegmentEntity.text
import persistence.entities.SegmentEntity.translatable

class SegmentDao {
    suspend fun create(model: SegmentModel, languageId: Int): Int = newSuspendedTransaction {
        SegmentEntity.insertIgnore {
            it[text] = model.text
            it[key] = model.key
            it[translatable] = model.translatable
            it[SegmentEntity.languageId] = languageId
        }[SegmentEntity.id].value
    }

    suspend fun update(model: SegmentModel) = newSuspendedTransaction {
        SegmentEntity.update({ SegmentEntity.id eq model.id }) {
            it[text] = model.text
            it[translatable] = model.translatable
        }
    }

    suspend fun delete(model: SegmentModel) = newSuspendedTransaction {
        SegmentEntity.deleteWhere { SegmentEntity.id eq model.id }
    }

    suspend fun getAll(languageId: Int): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.languageId eq languageId }.map { it.toModel() }
    }

    suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null
    ): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select {
            val conditions = mutableListOf<Op<Boolean>>()
            conditions += SegmentEntity.languageId eq languageId
            when (filter) {
                TranslationUnitTypeFilter.TRANSLATABLE -> {
                    conditions += (translatable eq true)
                }

                TranslationUnitTypeFilter.UNTRANSLATED -> {
                    conditions += (text eq "")
                }

                else -> Unit
            }
            if (!search.isNullOrBlank()) {
                val pattern = LikePattern("%$search%")
                conditions += (text like pattern) or (key like pattern)
            }
            conditions.fold<Op<Boolean>, Op<Boolean>>(Op.TRUE) { acc, it -> acc.and(it) }
        }.map { it.toModel() }
    }

    suspend fun getById(id: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.id eq id }.firstOrNull()?.toModel()
    }

    suspend fun getByKey(key: String, languageId: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { (SegmentEntity.key eq key) and (SegmentEntity.languageId eq languageId) }.firstOrNull()
            ?.toModel()
    }

    private fun ResultRow.toModel() = SegmentModel(
        id = this[id].value,
        text = this[text],
        key = this[key],
        translatable = this[translatable],
    )
}
