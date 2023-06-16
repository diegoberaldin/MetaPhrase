package com.github.diegoberaldin.metaphrase.domain.project.repository

interface LanguageNameRepository {
    fun getName(code: String): String
}
