package projectpersistence.dao

import data.SegmentModel
import data.TranslationUnitTypeFilter
import org.jetbrains.exposed.sql.LikePattern
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import projectpersistence.entities.SegmentEntity

class DefaultSegmentDao : SegmentDao {
    override suspend fun create(model: SegmentModel, languageId: Int): Int = newSuspendedTransaction {
        SegmentEntity.insertIgnore {
            it[text] = model.text
            it[key] = model.key
            it[translatable] = model.translatable
            it[SegmentEntity.languageId] = languageId
        }[SegmentEntity.id].value
    }

    override suspend fun createBatch(models: List<SegmentModel>, languageId: Int) = newSuspendedTransaction {
        SegmentEntity.batchInsert(models, shouldReturnGeneratedValues = false, ignore = true) { model ->
            this[SegmentEntity.text] = model.text
            this[SegmentEntity.key] = model.key
            this[SegmentEntity.translatable] = model.translatable
            this[SegmentEntity.languageId] = languageId
        }
    }

    override suspend fun update(model: SegmentModel) = newSuspendedTransaction {
        SegmentEntity.update({ SegmentEntity.id eq model.id }) {
            it[text] = model.text
            it[translatable] = model.translatable
        }
    }

    override suspend fun delete(model: SegmentModel) = newSuspendedTransaction {
        SegmentEntity.deleteWhere { SegmentEntity.id eq model.id }
    }

    override suspend fun getAll(languageId: Int): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.languageId eq languageId }.orderBy(SegmentEntity.key).map { it.toModel() }
    }

    override suspend fun getUntranslatable(languageId: Int): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.languageId eq languageId }.orderBy(SegmentEntity.key).map { it.toModel() }
    }

    override suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter,
        search: String?,
        skip: Int,
        limit: Int,
    ): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select {
            val conditions = mutableListOf<Op<Boolean>>()
            conditions += SegmentEntity.languageId eq languageId
            when (filter) {
                TranslationUnitTypeFilter.TRANSLATABLE -> {
                    conditions += (SegmentEntity.translatable eq true)
                }

                TranslationUnitTypeFilter.UNTRANSLATED -> {
                    conditions += (SegmentEntity.text eq "")
                }

                else -> Unit
            }
            if (!search.isNullOrBlank()) {
                val pattern = LikePattern("%$search%")
                conditions += (SegmentEntity.text like pattern) or (SegmentEntity.key like pattern)
            }
            conditions.fold<Op<Boolean>, Op<Boolean>>(Op.TRUE) { acc, it -> acc.and(it) }
        }.limit(n = limit, offset = skip.toLong())
            .orderBy(SegmentEntity.key)
            .map { it.toModel() }
    }

    override suspend fun getById(id: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.id eq id }.firstOrNull()?.toModel()
    }

    override suspend fun getByKey(key: String, languageId: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { (SegmentEntity.key eq key) and (SegmentEntity.languageId eq languageId) }.firstOrNull()
            ?.toModel()
    }

    override fun ResultRow.toModel() = SegmentModel(
        id = this[SegmentEntity.id].value,
        text = this[SegmentEntity.text],
        key = this[SegmentEntity.key],
        translatable = this[SegmentEntity.translatable],
    )
}
