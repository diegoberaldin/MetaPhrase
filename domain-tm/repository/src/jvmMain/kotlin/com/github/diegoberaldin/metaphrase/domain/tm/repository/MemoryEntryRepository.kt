package com.github.diegoberaldin.metaphrase.domain.tm.repository

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

interface MemoryEntryRepository {
    suspend fun create(model: TranslationMemoryEntryModel): Int

    suspend fun update(model: TranslationMemoryEntryModel): Int

    suspend fun delete(model: TranslationMemoryEntryModel): Int

    suspend fun deleteAll(origin: String? = null)

    suspend fun getByIdentifier(
        identifier: String,
        origin: String,
        sourceLang: String,
        targetLang: String,
    ): TranslationMemoryEntryModel?

    suspend fun getSources(sourceLang: String): List<TranslationMemoryEntryModel>

    suspend fun getTranslation(lang: String, key: String): TranslationMemoryEntryModel?

    suspend fun getSources(
        sourceLang: String,
        targetLang: String,
        search: String = "",
    ): List<TranslationMemoryEntryModel>

    suspend fun getLanguageCodes(): List<String>
}
