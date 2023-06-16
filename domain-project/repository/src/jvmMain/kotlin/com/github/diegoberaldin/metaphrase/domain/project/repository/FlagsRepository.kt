package com.github.diegoberaldin.metaphrase.domain.project.repository

interface FlagsRepository {
    fun getFlag(code: String): String
}
