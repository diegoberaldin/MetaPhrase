package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.core.common.utils.LruCache
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.MyMemoryDataSource
import java.io.File

internal class DefaultMachineTranslationRepository(
    private val myMemoryDataSource: MyMemoryDataSource,
) : MachineTranslationRepository {

    private val cache = LruCache<String>(size = 50)
    override suspend fun getTranslation(
        provider: MachineTranslationProvider,
        key: String?,
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
    ): String {
        // check for cached result
        val cacheKey = "$sourceMessage.$targetLang"
        val cached = cache[cacheKey]
        if (cached != null) {
            println("Returning cached result for key $cacheKey: $cached")
            return cached
        }

        val remote = when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                myMemoryDataSource.getTranslation(
                    key = key,
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetLang = targetLang,
                )
            }

            else -> ""
        }

        // save result in cache
        cache[cacheKey] = remote
        return remote
    }

    override suspend fun shareTranslation(
        provider: MachineTranslationProvider,
        key: String?,
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
    ) {
        when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                myMemoryDataSource.contributeTranslation(
                    key = key,
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetMessage = targetMessage,
                    targetLang = targetLang,
                )
            }

            else -> Unit
        }
    }

    override suspend fun generateKey(provider: MachineTranslationProvider, username: String, password: String): String =
        when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                myMemoryDataSource.generateKey(username = username, password = password)
            }

            else -> ""
        }

    override suspend fun importTm(
        provider: MachineTranslationProvider,
        file: File,
        key: String?,
        private: Boolean,
        name: String?,
        subject: String?,
    ) {
        when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                myMemoryDataSource.import(
                    file = file,
                    key = key,
                    private = private,
                    name = name,
                    subject = subject,
                )
            }

            else -> ""
        }
    }
}
