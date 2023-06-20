package com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

interface MemoryEntryDao {
    suspend fun create(model: TranslationMemoryEntryModel): Int

    suspend fun delete(model: TranslationMemoryEntryModel): Int

    suspend fun deleteAll(origin: String?)

    suspend fun update(model: TranslationMemoryEntryModel): Int

    suspend fun getById(id: Int, sourceLang: String, targetLang: String): TranslationMemoryEntryModel?

    suspend fun getSourceMessages(
        sourceLang: String,
    ): List<TranslationMemoryEntryModel>

    suspend fun getTargetMessage(
        lang: String,
        key: String,
    ): TranslationMemoryEntryModel?

    suspend fun getSourceMessages(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel>

    suspend fun getLanguageCodes(): List<String>
}
