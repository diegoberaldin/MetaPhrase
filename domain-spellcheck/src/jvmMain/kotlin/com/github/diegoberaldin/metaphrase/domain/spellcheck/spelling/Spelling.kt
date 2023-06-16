package com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling

import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection

interface Spelling {

    val isInitialized: Boolean
    suspend fun setLanguage(code: String)
    fun check(word: String): List<String>
    fun checkSentence(message: String): List<SpellCheckCorrection>
    fun getLemmata(message: String): List<String>
    suspend fun addUserDefinedWord(word: String)
}
