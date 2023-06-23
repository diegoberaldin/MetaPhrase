package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import java.io.File

interface MachineTranslationRepository {
    companion object {
        val AVAILABLE_PROVIDERS = listOf(
            MachineTranslationProvider.MY_MEMORY,
        )
    }

    suspend fun getTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        key: String? = null,
        message: String,
        sourceLang: String,
        targetLang: String,
    ): String

    suspend fun shareTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        key: String? = null,
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
    )

    suspend fun generateKey(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        username: String,
        password: String,
    ): String

    suspend fun importTm(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        file: File,
        key: String? = null,
        private: Boolean = false,
        name: String? = null,
        subject: String? = null,
    )
}
