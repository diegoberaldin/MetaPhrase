package com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

/**
 * Memory entry Data Access Object.
 */
interface MemoryEntryDao {
    /**
     * Create a new translation memory entry.
     *
     * @param model Model to insert
     * @return ID of the newly created entry
     */
    suspend fun create(model: TranslationMemoryEntryModel): Int

    /**
     * Delete a translation memory entry.
     *
     * @param model Model to delete
     * @return
     */
    suspend fun delete(model: TranslationMemoryEntryModel)

    /**
     * Delete all the entries with a given origin.
     *
     * @param origin entry origin
     */
    suspend fun deleteAll(origin: String?)

    /**
     * Update a translation memory entry.
     *
     * @param model Model to update
     */
    suspend fun update(model: TranslationMemoryEntryModel)

    /**
     * Get a TM entry by identifier.
     *
     * @param identifier Identifier (key or tuid)
     * @param origin Origin
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
     * Get TM entries.
     *
     * @param sourceLang Source language code
     * @return TM entries
     */
    suspend fun getEntries(
        sourceLang: String,
    ): List<TranslationMemoryEntryModel>

    /**
     * Get the target message for a given TM entry key.
     *
     * @param lang target language code
     * @param key message key
     * @return [TranslationMemoryEntryModel] or null
     */
    suspend fun getTargetMessage(
        lang: String,
        key: String,
    ): TranslationMemoryEntryModel?

    /**
     * Get TM entries.
     *
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @param search Search string
     * @return TM entries
     */
    suspend fun getEntries(
        sourceLang: String,
        targetLang: String,
        search: String,
    ): List<TranslationMemoryEntryModel>

    /**
     * Get the list of distinct language codes in the TM.
     *
     * @return list of langauge codes.
     */
    suspend fun getLanguageCodes(): List<String>
}
