package spellcheck.spelling

import spellcheck.SpellCheckCorrection

interface Spelling {

    val isInitialized: Boolean
    fun setLanguage(code: String)
    fun check(word: String): Pair<Boolean, List<String>>
    fun checkSentence(message: String): List<SpellCheckCorrection>
}
