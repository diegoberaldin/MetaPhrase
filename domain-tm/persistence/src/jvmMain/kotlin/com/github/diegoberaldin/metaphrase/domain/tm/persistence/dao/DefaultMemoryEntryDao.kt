package com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryEntryEntity
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryMessageEntity
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.LikePattern
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

class DefaultMemoryEntryDao : MemoryEntryDao {
    override suspend fun create(model: TranslationMemoryEntryModel): Int = newSuspendedTransaction {
        val entryId = MemoryEntryEntity.insertIgnore {
            it[origin] = model.origin
        }[MemoryEntryEntity.id].value

        MemoryMessageEntity.insertIgnore {
            it[MemoryMessageEntity.entryId] = entryId
            it[lang] = model.sourceLang
            it[text] = model.sourceText
        }
        MemoryMessageEntity.insertIgnore {
            it[MemoryMessageEntity.entryId] = entryId
            it[lang] = model.targetLang
            it[text] = model.targetText
        }

        entryId
    }

    override suspend fun delete(model: TranslationMemoryEntryModel) = newSuspendedTransaction {
        MemoryEntryEntity.deleteWhere { MemoryEntryEntity.id eq model.id }
    }

    override suspend fun deleteAll(origin: String?) = newSuspendedTransaction {
        if (origin.isNullOrEmpty()) {
            MemoryEntryEntity.deleteAll()
        } else {
            MemoryEntryEntity.deleteWhere { MemoryEntryEntity.origin eq origin }
        }
        Unit
    }

    override suspend fun update(model: TranslationMemoryEntryModel) = newSuspendedTransaction {
        MemoryMessageEntity.update(where = { (MemoryMessageEntity.entryId eq model.id) and (MemoryMessageEntity.lang eq model.sourceLang) }) {
            it[text] = model.sourceText
        }
        MemoryMessageEntity.update(where = { (MemoryMessageEntity.entryId eq model.id) and (MemoryMessageEntity.lang eq model.targetLang) }) {
            it[text] = model.targetText
        }
    }

    override suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel? =
        newSuspendedTransaction {
            val origin = MemoryEntryEntity.select { MemoryEntryEntity.id eq id }.firstOrNull()
                ?.let { it[MemoryEntryEntity.origin] }.orEmpty()
            val sourceText =
                MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq id) and (MemoryMessageEntity.lang eq sourceLang) }
                    .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
            val targetText =
                MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq id) and (MemoryMessageEntity.lang eq targetLang) }
                    .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
            if (sourceText.isEmpty() || targetText.isEmpty()) {
                return@newSuspendedTransaction null
            }

            TranslationMemoryEntryModel(
                id = id,
                origin = origin,
                sourceText = sourceText,
                sourceLang = sourceLang,
                targetText = targetText,
                targetLang = targetLang,
            )
        }

    override suspend fun getSourceMessages(sourceLang: String): List<TranslationMemoryEntryModel> =
        newSuspendedTransaction {
            MemoryEntryEntity.join(
                otherTable = MemoryMessageEntity,
                onColumn = MemoryEntryEntity.id,
                otherColumn = MemoryMessageEntity.entryId,
                joinType = JoinType.INNER,
            ).select {
                MemoryMessageEntity.lang eq sourceLang
            }.mapNotNull {
                val entryId = it[MemoryEntryEntity.id].value
                val identifier = it[MemoryEntryEntity.identifier]
                val origin = it[MemoryEntryEntity.origin]

                val text = it[MemoryMessageEntity.text]
                if (text.isEmpty()) {
                    return@mapNotNull null
                }
                TranslationMemoryEntryModel(
                    id = entryId,
                    identifier = identifier,
                    origin = origin,
                    sourceText = text,
                    sourceLang = sourceLang,
                )
            }
        }

    override suspend fun getTargetMessage(lang: String, key: String): TranslationMemoryEntryModel? =
        newSuspendedTransaction {
            MemoryEntryEntity.join(
                otherTable = MemoryMessageEntity,
                onColumn = MemoryEntryEntity.id,
                otherColumn = MemoryMessageEntity.entryId,
                joinType = JoinType.INNER,
            ).select {
                (MemoryMessageEntity.lang eq lang) and (MemoryEntryEntity.identifier eq key)
            }.mapNotNull {
                val entryId = it[MemoryEntryEntity.id].value
                val identifier = it[MemoryEntryEntity.identifier]
                val origin = it[MemoryEntryEntity.origin]

                val text = it[MemoryMessageEntity.text]
                if (text.isEmpty()) {
                    return@mapNotNull null
                }
                TranslationMemoryEntryModel(
                    id = entryId,
                    identifier = identifier,
                    origin = origin,
                    targetText = text,
                    targetLang = lang,
                )
            }.firstOrNull()
        }

    override suspend fun getSourceMessages(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel> =
        newSuspendedTransaction {
            if (search.isEmpty()) {
                MemoryEntryEntity.selectAll()
            } else {
                val pattern = LikePattern("%$search%")
                MemoryEntryEntity.join(
                    otherTable = MemoryMessageEntity,
                    onColumn = MemoryEntryEntity.id,
                    otherColumn = MemoryMessageEntity.entryId,
                    joinType = JoinType.INNER,
                ).select {
                    MemoryMessageEntity.text.like(pattern)
                }
            }.mapNotNull {
                val entryId = it[MemoryEntryEntity.id].value
                val identifier = it[MemoryEntryEntity.identifier]
                val origin = it[MemoryEntryEntity.origin]

                val sourceText =
                    MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq entryId) and (MemoryMessageEntity.lang eq sourceLang) }
                        .firstOrNull()?.let { e -> e[MemoryMessageEntity.text] }.orEmpty()
                val targetText =
                    MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq entryId) and (MemoryMessageEntity.lang eq targetLang) }
                        .firstOrNull()?.let { e -> e[MemoryMessageEntity.text] }.orEmpty()
                if (sourceText.isEmpty() || targetText.isEmpty()) {
                    return@mapNotNull null
                }
                TranslationMemoryEntryModel(
                    id = entryId,
                    identifier = identifier,
                    origin = origin,
                    sourceText = sourceText,
                    sourceLang = sourceLang,
                    targetText = targetText,
                    targetLang = targetLang,
                )
            }
        }

    override suspend fun getLanguageCodes(): List<String> = newSuspendedTransaction {
        MemoryMessageEntity.slice(MemoryMessageEntity.lang).selectAll().map { it[MemoryMessageEntity.lang] }
            .distinct()
    }
}
