package com.github.diegoberaldin.metaphrase.domain.tm.repository

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.MemoryEntryDao

internal class DefaultMemoryEntryRepository(
    private val dao: MemoryEntryDao,
) : MemoryEntryRepository {
    override suspend fun create(model: TranslationMemoryEntryModel): Int = dao.create(model)

    override suspend fun update(model: TranslationMemoryEntryModel) = dao.update(model)

    override suspend fun delete(model: TranslationMemoryEntryModel) = dao.delete(model)

    override suspend fun deleteAll(origin: String?) {
        dao.deleteAll(origin)
    }

    override suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel? =
        dao.getById(id = id, sourceLang = sourceLang, targetLang = targetLang)

    override suspend fun getSources(sourceLang: String): List<TranslationMemoryEntryModel> =
        dao.getSourceMessages(sourceLang)

    override suspend fun getTranslation(lang: String, key: String): TranslationMemoryEntryModel? =
        dao.getTargetMessage(lang = lang, key = key)

    override suspend fun getSources(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel> =
        dao.getSourceMessages(sourceLang = sourceLang, targetLang = targetLang, search = search)

    override suspend fun getLanguageCodes(): List<String> = dao.getLanguageCodes()
}
