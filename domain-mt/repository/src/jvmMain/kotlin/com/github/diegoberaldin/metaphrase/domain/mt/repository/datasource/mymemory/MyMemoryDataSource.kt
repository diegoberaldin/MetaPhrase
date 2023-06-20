package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory

import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.MachineTranslationDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.client
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto.ServiceResponse
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class MyMemoryDataSource : MachineTranslationDataSource {

    private object Urls {
        const val Base = "https://api.mymemory.translated.net"
        const val GetTranslation = "$Base/get"
        const val SetTranslation = "$Base/set"
    }

    private object Params {
        const val Query = "q"
        const val LanguagePair = "langpair"
        const val ApiKey = "key"
        const val Segment = "seg"
        const val Translation = "tra"
    }

    override suspend fun getTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
        key: String?,
    ): String {
        val langPair = getLanguagePair(sourceLang, targetLang) ?: return ""

        val output = runCatching {
            val response = client.get(
                HttpRequestBuilder().apply {
                    url(Urls.GetTranslation)
                    parameter(Params.Query, sourceMessage)
                    parameter(Params.LanguagePair, langPair)
                    if (!key.isNullOrEmpty()) {
                        parameter(Params.ApiKey, key)
                    }
                },
            )
            response.body<ServiceResponse>()
        }.apply {
            exceptionOrNull()?.also { exc -> exc.printStackTrace() }
        }.getOrElse {
            ServiceResponse()
        }
        return output.matches.firstOrNull()?.translation.orEmpty()
    }

    private fun getLanguagePair(sourceLang: String, targetLang: String) =
        listOf(sourceLang, targetLang).zipWithNext { a, b ->
            if (sourceLang.isEmpty() || targetLang.isEmpty()) {
                null
            } else {
                "$sourceLang|$targetLang"
            }
        }.firstOrNull()

    override suspend fun contributeTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
        key: String?,
    ) {
        val langPair = getLanguagePair(sourceLang, targetLang) ?: return

        runCatching {
            client.get(
                HttpRequestBuilder().apply {
                    url(Urls.SetTranslation)
                    parameter(Params.Segment, sourceMessage)
                    parameter(Params.Translation, targetMessage)
                    parameter(Params.LanguagePair, langPair)
                    if (!key.isNullOrEmpty()) {
                        parameter(Params.ApiKey, key)
                    }
                },
            )
        }.apply {
            exceptionOrNull()?.also { exc -> exc.printStackTrace() }
        }
    }
}
