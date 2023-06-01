package repository.local

import data.TranslationMemoryEntryModel
import persistence.dao.MemoryEntryDao

class MemoryEntryRepository(
    private val dao: MemoryEntryDao,
) {
    suspend fun create(model: TranslationMemoryEntryModel): Int = dao.create(model)

    suspend fun update(model: TranslationMemoryEntryModel) = dao.update(model)

    suspend fun delete(model: TranslationMemoryEntryModel) = dao.delete(model)

    suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel? =
        dao.getById(id = id, sourceLang = sourceLang, targetLang = targetLang)

    suspend fun getAll(sourceLang: String, targetLang: String, search: String = ""): List<TranslationMemoryEntryModel> =
        dao.getAll(sourceLang = sourceLang, targetLang = targetLang, search = search)

    suspend fun getLanguageCodes(): List<String> = dao.getLanguageCodes()
}
