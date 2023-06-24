package com.github.diegoberaldin.metaphrase.domain.tm.repository

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

/**
 * Contrast of the memory entry repository.
 */
interface MemoryEntryRepository {
    /**
     * Create a new translation memory.
     *
     * @param model Model
     * @return ID of the newly created entry
     */
    suspend fun create(model: TranslationMemoryEntryModel): Int

    /**
     * Update a translation memory entry.
     *
     * @param model Model
     */
    suspend fun update(model: TranslationMemoryEntryModel)

    /**
     * Delete a translation memory entry.
     *
     * @param model Model to delete
     * @return
     */
    suspend fun delete(model: TranslationMemoryEntryModel): Int

    /**
     * Delete all the translation memory entries having a given origin.
     *
     * @param origin Origin of entries to delete
     */
    suspend fun deleteAll(origin: String? = null)

    /**
     * Get translation memory entries by identifier.
     *
     * @param identifier Identifier (message key or tuid)
     * @param origin Origin of translation memory entries
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @return [TranslationMemoryEntryModel] or null
     */
    suspend fun getByIdentifier(
        identifier: String,
        origin: String,
        sourceLang: String,
        targetLang: String,
    ): TranslationMemoryEntryModel?

    /**
     * Get translation memory entries.
     *
     * @param sourceLang Source language code
     * @return TM entries
     */
    suspend fun getEntries(sourceLang: String): List<TranslationMemoryEntryModel>

    /**
     * Get an entry with a given target message for a source message.
     *
     * @param lang target language code
     * @param key message key
     * @return [TranslationMemoryEntryModel] or null
     */
    suspend fun getTranslation(lang: String, key: String): TranslationMemoryEntryModel?

    /**
     * Get TM entries.
     *
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @param search Search query
     * @return
     */
    suspend fun getEntries(
        sourceLang: String,
        targetLang: String,
        search: String = "",
    ): List<TranslationMemoryEntryModel>

    /**
     * Get the list of distinct language codes that are found in the TM.
     *
     * @return list of language codes
     */
    suspend fun getLanguageCodes(): List<String>
}
