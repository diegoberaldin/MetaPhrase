package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.deepl

import com.deepl.api.Translator
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.MachineTranslationDataSource
import java.io.File

class DeeplDataSource : MachineTranslationDataSource {

    private var lastKey = ""
    private lateinit var translator: Translator
    override suspend fun getTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
        key: String?,
    ): String {
        if (key.isNullOrEmpty()) return ""

        if (key != lastKey) {
            translator = Translator(key)
            lastKey = key
        }
        val result = translator.translateText(
            sourceMessage,
            sourceLang,
            targetLang,
        )
        return result.text
    }

    override suspend fun contributeTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
        key: String?,
    ) = Unit

    override suspend fun generateKey(username: String, password: String): String = ""

    override suspend fun import(file: File, key: String?, private: Boolean, name: String?, subject: String?) = Unit
}
