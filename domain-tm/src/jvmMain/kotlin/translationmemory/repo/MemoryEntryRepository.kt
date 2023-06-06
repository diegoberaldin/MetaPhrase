package translationmemory.repo

import data.TranslationMemoryEntryModel

interface MemoryEntryRepository {
    suspend fun create(model: TranslationMemoryEntryModel): Int

    suspend fun update(model: TranslationMemoryEntryModel): Int

    suspend fun delete(model: TranslationMemoryEntryModel): Int

    suspend fun deleteAll(): Int

    suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel?

    suspend fun getAll(sourceLang: String, targetLang: String, search: String = ""): List<TranslationMemoryEntryModel>

    suspend fun getLanguageCodes(): List<String>
}

