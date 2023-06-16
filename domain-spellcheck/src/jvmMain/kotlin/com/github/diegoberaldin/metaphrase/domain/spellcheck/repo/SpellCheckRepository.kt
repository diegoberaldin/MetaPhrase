package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection

interface SpellCheckRepository {
    fun setLanguage(code: String)
    suspend fun check(message: String): List<SpellCheckCorrection>
}
