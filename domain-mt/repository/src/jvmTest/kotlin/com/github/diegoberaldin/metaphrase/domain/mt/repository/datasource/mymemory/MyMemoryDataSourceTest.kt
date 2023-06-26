package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private val getTranslationResponse = """
{
    "quotaFinished": false,
    "responseStatus": 200,
    "matches": [
        {
            "id": "0",
            "segment": "source message",
            "translation": "messaggio di partenza"
        }
    ]
}
""".trimIndent()

private val generateKeyResponse = """
{
    "key": "test_api_key"
}
""".trimIndent()

class MyMemoryDataSourceTest {
    private val mockEngine = MockEngine { request ->
        val path = request.url.fullPath
        respond(
            content = when {
                path.startsWith("/get") -> getTranslationResponse
                path.startsWith("/keygen") -> generateKeyResponse
                else -> ""
            },
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json"),
        )
    }
    private val mockClient = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                },
            )
        }
    }
    private val sut = MyMemoryDataSource(
        client = mockClient,
    )

    @Test
    fun givenDataSourceWhenGetTranslationIsInvokedThenRequestIsSent() = runTest {
        val key = "api_key"
        val sourceMessage = "source message"
        val sourceLang = "en"
        val targetLang = "it"
        val targetMessage = "messaggio di partenza"
        val res = sut.getTranslation(
            key = key,
            sourceMessage = sourceMessage,
            sourceLang = sourceLang,
            targetLang = targetLang,
        )

        assertEquals(targetMessage, res)

        val req = mockEngine.requestHistory.last()
        assertEquals(key, req.url.parameters["key"])
        assertEquals(sourceMessage, req.url.parameters["q"])
        assertEquals("$sourceLang|$targetLang", req.url.parameters["langpair"])
    }

    @Test
    fun givenDataSourceWhenContributeTranslationIsInvokedThenRequestIsSent() = runTest {
        val key = "api_key"
        val sourceMessage = "source message"
        val sourceLang = "en"
        val targetLang = "it"
        val targetMessage = "messaggio di partenza"

        sut.contributeTranslation(
            key = key,
            sourceMessage = sourceMessage,
            sourceLang = sourceLang,
            targetLang = targetLang,
            targetMessage = targetMessage,
        )

        val req = mockEngine.requestHistory.last()
        assertEquals(key, req.url.parameters["key"])
        assertEquals("$sourceLang|$targetLang", req.url.parameters["langpair"])
        assertEquals(sourceMessage, req.url.parameters["seg"])
        assertEquals(targetMessage, req.url.parameters["tra"])
    }

    @Test
    fun givenDataSourceWhenGenerateKeyIsInvokedThenRequestIsSent() = runTest {
        val username = "username"
        val password = "test"

        val res = sut.generateKey(
            username = username,
            password = password,
        )

        assertEquals("test_api_key", res)

        val req = mockEngine.requestHistory.last()
        assertEquals(username, req.url.parameters["user"])
        assertEquals(password, req.url.parameters["pass"])
    }

    @Test
    fun givenDataSourceWhenImportIsInvokedThenRequestIsSent() = runTest {
        val key = "api_key"
        val file = File.createTempFile("test", ".tmx")

        sut.import(
            file = file,
            key = key,
        )

        val req = mockEngine.requestHistory.last()
        assertTrue(req.url.fullPath.startsWith("/v2/tmx/import"))

        file.delete()
    }
}
