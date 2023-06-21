package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

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
}
