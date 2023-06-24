package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory

import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.MachineTranslationDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.client
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto.ServiceKey
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto.ServiceResponse
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.quote
import io.ktor.utils.io.streams.asInput
import java.io.File

internal class MyMemoryDataSource : MachineTranslationDataSource {
    private object Urls {
        const val Base = "https://api.mymemory.translated.net"
        const val GetTranslation = "$Base/get"
        const val SetTranslation = "$Base/set"
        const val GenerateKey = "$Base/keygen"
        const val Import = "$Base/v2/tmx/import"
    }

    private object Params {
        const val Query = "q"
        const val LanguagePair = "langpair"
        const val ApiKey = "key"
        const val Segment = "seg"
        const val Translation = "tra"
        const val Username = "user"
        const val Password = "pass"
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
            if (a.isEmpty() || b.isEmpty()) {
                null
            } else {
                "$a|$b"
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

    override suspend fun generateKey(username: String, password: String): String = runCatching {
        val response = client.get(
            HttpRequestBuilder().apply {
                url(Urls.GenerateKey)
                parameter(Params.Username, username)
                parameter(Params.Password, password)
            },
        )
        val key = response.body<ServiceKey>()
        key.key
    }.getOrElse { "" }

    override suspend fun import(file: File, key: String?, private: Boolean, name: String?, subject: String?) {
        runCatching {
            client.post(
                HttpRequestBuilder().apply {
                    method = HttpMethod.Post
                    url(Urls.Import)
                    formData {
                        "file".quote()
                        InputProvider(file.length()) { file.inputStream().asInput() }
                        Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=${file.name.quote()}")
                        }
                    }
                },
            )
        }
    }
}
