package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

interface UserDefinedWordsRepository {
    suspend fun getAll(lang: String): List<String>
    suspend fun clear(lang: String)
    suspend fun add(word: String, lang: String)
}
