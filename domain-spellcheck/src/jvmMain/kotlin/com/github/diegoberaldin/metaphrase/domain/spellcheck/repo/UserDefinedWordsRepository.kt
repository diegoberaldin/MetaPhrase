package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

/**
 * Contract for the user-defined words repository.
 */
interface UserDefinedWordsRepository {
    /**
     * Get all user-defined words.
     *
     * @param lang Language code
     * @return
     */
    suspend fun getAll(lang: String): List<String>

    /**
     * Clear all the user-defined words for a language.
     *
     * @param lang Language code
     */
    suspend fun clear(lang: String)

    /**
     * Add a new user-defined word.
     *
     * @param word Word to ignore
     * @param lang Language code
     */
    suspend fun add(word: String, lang: String)
}
