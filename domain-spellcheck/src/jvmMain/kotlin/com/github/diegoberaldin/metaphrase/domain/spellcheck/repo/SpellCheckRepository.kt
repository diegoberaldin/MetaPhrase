package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection

/**
 * Contract for the spell check repository.
 */
interface SpellCheckRepository {
    /**
     * Set language for the checks.
     *
     * @param code language code
     */
    suspend fun setLanguage(code: String)

    /**
     * Check a message for spelling errors.
     *
     * @param message Message to check
     * @return
     */
    suspend fun check(message: String): List<SpellCheckCorrection>

    /**
     * Add a user-defined word.
     *
     * @param word Word to ignore
     */
    suspend fun addUserDefineWord(word: String)
}
