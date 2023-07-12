package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.core.common.utils.LruCache
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.MachineTranslationDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.deepl.DeeplDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.MyMemoryDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File

internal class DefaultMachineTranslationRepository(
    private val myMemoryDataSource: MyMemoryDataSource,
    private val deeplDataSource: DeeplDataSource,
) : MachineTranslationRepository {

    private lateinit var provider: MachineTranslationProvider
    private val cache = LruCache<String>(size = 50)

    override val supportsContributions = MutableStateFlow(false)
    override val supportsKeyGeneration = MutableStateFlow(false)

    init {
        setProvider(MachineTranslationProvider.MY_MEMORY)
    }

    override fun setProvider(provider: MachineTranslationProvider) {
        this.provider = provider
        when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                supportsContributions.value = true
                supportsKeyGeneration.value = true
            }

            else -> {
                supportsContributions.value = false
                supportsKeyGeneration.value = false
            }
        }
    }

    override suspend fun getTranslation(
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

        val dataSource: MachineTranslationDataSource =
            when (provider) {
                MachineTranslationProvider.MY_MEMORY -> myMemoryDataSource
                MachineTranslationProvider.DEEPL -> deeplDataSource
            }
        val remote = dataSource.getTranslation(
            key = key,
            sourceMessage = sourceMessage,
            sourceLang = sourceLang,
            targetLang = targetLang,
        )

        // save result in cache
        cache[cacheKey] = remote
        return remote
    }

    override suspend fun shareTranslation(
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

    override suspend fun generateKey(username: String, password: String): String =
        when (provider) {
            MachineTranslationProvider.MY_MEMORY -> {
                myMemoryDataSource.generateKey(username = username, password = password)
            }

            else -> ""
        }

    override suspend fun importTm(
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

            else -> Unit
        }
    }
}
