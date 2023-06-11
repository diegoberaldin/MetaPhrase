package spellcheck.spelling

import spellcheck.SpellCheckCorrection

interface Spelling {

    val isInitialized: Boolean
    fun setLanguage(code: String)
    fun check(word: String): List<String>
    fun checkSentence(message: String): List<SpellCheckCorrection>
    fun getLemmata(message: String): List<String>
}
