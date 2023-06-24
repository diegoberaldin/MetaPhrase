package com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling

import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection

/**
 * Contract for the spelling checker.
 */
interface Spelling {

    /**
     * Flag indicating whether the spell checker is initialized
     */
    val isInitialized: Boolean

    /**
     * Set language for spell check.
     *
     * @param code language code
     */
    suspend fun setLanguage(code: String)

    /**
     * Check a word for spelling mistakes.
     *
     * @param word Word to check
     * @return list of suggestions
     */
    fun check(word: String): List<String>

    /**
     * Check a whole sentence for spelling mistakes.
     *
     * @param message Message to check
     * @return list of corrections
     */
    fun checkSentence(message: String): List<SpellCheckCorrection>

    /**
     * Analyze a message to get a list of word stems.
     *
     * @param message Message to analyze
     * @return list of stems
     */
    fun getLemmata(message: String): List<String>

    /**
     * Add a user defined word.
     *
     * @param word Word to ignore for spellcheck
     */
    suspend fun addUserDefinedWord(word: String)
}
