package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.SegmentEntity
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.LikePattern
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class DefaultSegmentDao : SegmentDao {
    override suspend fun create(model: SegmentModel, languageId: Int): Int = newSuspendedTransaction {
        SegmentEntity.insertIgnore {
            it[text] = model.text
            it[key] = model.key
            it[translatable] = model.translatable
            it[SegmentEntity.languageId] = languageId
        }[SegmentEntity.id].value
    }

    override suspend fun createBatch(models: List<SegmentModel>, languageId: Int): Unit = newSuspendedTransaction {
        SegmentEntity.batchInsert(models, shouldReturnGeneratedValues = false, ignore = true) { model ->
            this[SegmentEntity.text] = model.text
            this[SegmentEntity.key] = model.key
            this[SegmentEntity.translatable] = model.translatable
            this[SegmentEntity.languageId] = languageId
        }
    }

    override suspend fun update(model: SegmentModel): Unit = newSuspendedTransaction {
        SegmentEntity.update({ SegmentEntity.id eq model.id }) {
            it[text] = model.text
            it[translatable] = model.translatable
        }
    }

    override suspend fun delete(model: SegmentModel): Unit = newSuspendedTransaction {
        SegmentEntity.deleteWhere { SegmentEntity.id eq model.id }
    }

    override suspend fun getAll(languageId: Int): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.languageId eq languageId }.orderBy(SegmentEntity.key).map { it.toModel() }
    }

    override suspend fun getUntranslatable(languageId: Int): List<SegmentModel> = newSuspendedTransaction {
        SegmentEntity.select { (SegmentEntity.languageId eq languageId) and (SegmentEntity.translatable eq false) }
            .orderBy(SegmentEntity.key).map { it.toModel() }
    }

    override suspend fun search(
        languageId: Int,
        baseLanguageId: Int,
        filter: TranslationUnitTypeFilter,
        search: String?,
        skip: Int,
        limit: Int,
    ): List<SegmentModel> = newSuspendedTransaction {
        val segments1 = SegmentEntity.alias("segments1")
        val segments2 = SegmentEntity.alias("segments2")
        segments1.join(
            otherTable = segments2,
            onColumn = segments1[SegmentEntity.key],
            otherColumn = segments2[SegmentEntity.key],
            joinType = JoinType.INNER,
        ).select {
            val conditions = mutableListOf<Op<Boolean>>()
            conditions += segments1[SegmentEntity.languageId] eq languageId
            if (baseLanguageId > 0) {
                conditions += segments2[SegmentEntity.languageId] eq baseLanguageId
            }
            when (filter) {
                TranslationUnitTypeFilter.TRANSLATABLE -> {
                    conditions += (segments1[SegmentEntity.translatable] eq true)
                }

                TranslationUnitTypeFilter.UNTRANSLATED -> {
                    conditions += (segments1[SegmentEntity.text] eq "")
                }

                else -> Unit
            }
            if (!search.isNullOrBlank()) {
                val pattern = LikePattern("%$search%")
                conditions += (segments1[SegmentEntity.text] like pattern)
                    .or(segments1[SegmentEntity.key] like pattern)
                    .or(segments2[SegmentEntity.text] like pattern)
            }
            conditions.fold<Op<Boolean>, Op<Boolean>>(Op.TRUE) { acc, it -> acc.and(it) }
        }.run {
            if (limit > 0) {
                limit(n = limit, offset = skip.toLong())
            } else {
                this
            }
        }
            .orderBy(segments1[SegmentEntity.key])
            .map {
                SegmentModel(
                    id = it[segments1[SegmentEntity.id]].value,
                    text = it[segments1[SegmentEntity.text]],
                    key = it[segments1[SegmentEntity.key]],
                    translatable = it[segments1[SegmentEntity.translatable]],
                )
            }
    }

    override suspend fun getById(id: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { SegmentEntity.id eq id }.firstOrNull()?.toModel()
    }

    override suspend fun getByKey(key: String, languageId: Int): SegmentModel? = newSuspendedTransaction {
        SegmentEntity.select { (SegmentEntity.key eq key) and (SegmentEntity.languageId eq languageId) }.firstOrNull()
            ?.toModel()
    }

    private fun ResultRow.toModel() = SegmentModel(
        id = this[SegmentEntity.id].value,
        text = this[SegmentEntity.text],
        key = this[SegmentEntity.key],
        translatable = this[SegmentEntity.translatable],
    )
}
