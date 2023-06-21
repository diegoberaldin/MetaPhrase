package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource

interface MachineTranslationDataSource {
    suspend fun getTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
        key: String? = null,
    ): String

    suspend fun contributeTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
        key: String? = null,
    )

    suspend fun generateKey(
        username: String,
        password: String,
    ): String
}
