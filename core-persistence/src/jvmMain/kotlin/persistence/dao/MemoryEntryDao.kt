package persistence.dao

import data.TranslationMemoryEntryModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import persistence.entities.MemoryEntryEntity
import persistence.entities.MemoryMessageEntity

class MemoryEntryDao {

    suspend fun create(model: TranslationMemoryEntryModel): Int = newSuspendedTransaction {
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

    suspend fun delete(model: TranslationMemoryEntryModel) = newSuspendedTransaction {
        MemoryMessageEntity.deleteWhere { MemoryMessageEntity.id eq model.id }
    }

    suspend fun update(model: TranslationMemoryEntryModel) = newSuspendedTransaction {
        MemoryMessageEntity.update(where = { (MemoryMessageEntity.entryId eq model.id) and (MemoryMessageEntity.lang eq model.sourceLang) }) {
            it[text] = model.sourceText
        }
        MemoryMessageEntity.update(where = { (MemoryMessageEntity.entryId eq model.id) and (MemoryMessageEntity.lang eq model.targetLang) }) {
            it[text] = model.targetText
        }
    }

    suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel? = newSuspendedTransaction {
        val origin = MemoryEntryEntity.select { MemoryEntryEntity.id eq id }.firstOrNull()?.let { it[MemoryEntryEntity.origin] }.orEmpty()
        val sourceText = MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq id) and (MemoryMessageEntity.lang eq sourceLang) }
            .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
        val targetText = MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq id) and (MemoryMessageEntity.lang eq targetLang) }
            .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
        if (sourceText.isEmpty() || targetText.isEmpty()) {
            return@newSuspendedTransaction null
        }

        TranslationMemoryEntryModel(
            origin = origin,
            sourceText = sourceText,
            sourceLang = sourceLang,
            targetText = targetText,
            targetLang = targetLang,
        )
    }

    suspend fun getAll(sourceLang: String, targetLang: String): List<TranslationMemoryEntryModel> = newSuspendedTransaction {
        MemoryEntryEntity.selectAll().mapNotNull {
            val entryId = it[MemoryEntryEntity.id].value
            val origin = it[MemoryEntryEntity.origin]

            val sourceText = MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq entryId) and (MemoryMessageEntity.lang eq sourceLang) }
                .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
            val targetText = MemoryMessageEntity.select { (MemoryMessageEntity.entryId eq entryId) and (MemoryMessageEntity.lang eq targetLang) }
                .firstOrNull()?.let { it[MemoryMessageEntity.text] }.orEmpty()
            if (sourceText.isEmpty() || targetText.isEmpty()) {
                return@mapNotNull null
            }
            TranslationMemoryEntryModel(
                origin = origin,
                sourceText = sourceText,
                sourceLang = sourceLang,
                targetText = targetText,
                targetLang = targetLang,
            )
        }
    }
}