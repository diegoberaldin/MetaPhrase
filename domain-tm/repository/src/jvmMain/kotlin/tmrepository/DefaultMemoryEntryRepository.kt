package tmrepository

import data.TranslationMemoryEntryModel
import tmpersistence.dao.MemoryEntryDao

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

    override suspend fun getAll(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel> =
        dao.getAll(sourceLang = sourceLang, targetLang = targetLang, search = search)

    override suspend fun getLanguageCodes(): List<String> = dao.getLanguageCodes()
}
