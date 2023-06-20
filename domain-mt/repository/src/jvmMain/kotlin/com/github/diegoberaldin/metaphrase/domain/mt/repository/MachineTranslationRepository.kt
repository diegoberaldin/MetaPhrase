package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider

interface MachineTranslationRepository {
    suspend fun getTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        message: String,
        sourceLang: String,
        targetLang: String,
    ): String

    suspend fun shareTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
    )
}
