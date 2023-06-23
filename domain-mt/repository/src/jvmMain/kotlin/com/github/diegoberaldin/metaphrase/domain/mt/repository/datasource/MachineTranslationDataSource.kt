package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource

import java.io.File

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

    suspend fun import(
        file: File,
        key: String? = null,
        private: Boolean = false,
        name: String? = null,
        subject: String? = null,
    )
}
