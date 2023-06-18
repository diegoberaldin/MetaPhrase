package com.github.diegoberaldin.metaphrase.domain.language.repository

interface FlagsRepository {
    fun getFlag(code: String): String
}
