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

    override suspend fun getByIdentifier(
        identifier: String,
        origin: String,
        sourceLang: String,
        targetLang: String,
    ): TranslationMemoryEntryModel? =
        dao.getByIdentifier(identifier = identifier, origin = origin, sourceLang = sourceLang, targetLang = targetLang)

    override suspend fun getEntries(sourceLang: String): List<TranslationMemoryEntryModel> =
        dao.getEntries(sourceLang)

    override suspend fun getTranslation(lang: String, key: String): TranslationMemoryEntryModel? =
        dao.getTargetMessage(lang = lang, key = key)

    override suspend fun getEntries(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel> =
        dao.getEntries(sourceLang = sourceLang, targetLang = targetLang, search = search)

    override suspend fun getLanguageCodes(): List<String> = dao.getLanguageCodes()
}
