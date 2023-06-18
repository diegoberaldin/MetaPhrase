package com.github.diegoberaldin.metaphrase.domain.language.repository

interface LanguageNameRepository {
    fun getName(code: String): String
}
