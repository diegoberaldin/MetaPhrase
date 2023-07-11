package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.deepl.DeeplDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.MyMemoryDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultMachineTranslationRepositoryTest {
    private val mockMyMemoryDataSource = mockk<MyMemoryDataSource>()
    private val mockDeeplDataSource = mockk<DeeplDataSource>()
    private val sut = DefaultMachineTranslationRepository(
        myMemoryDataSource = mockMyMemoryDataSource,
        deeplDataSource = mockDeeplDataSource,
    )

    @Test
    fun givenRepositoryWhenGetTranslationInvokedThenSuggestionIsReturned() = runTest {
        val key = "api_key"
        val sourceMessage = "source message"
        val sourceLang = "en"
        val targetLang = "it"
        val targetMessage = "messaggio di partenza"
        coEvery {
            mockMyMemoryDataSource.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns targetMessage

        sut.setProvider(MachineTranslationProvider.MY_MEMORY)
        val res = sut.getTranslation(
            key = key,
            sourceMessage = sourceMessage,
            sourceLang = sourceLang,
            targetLang = targetLang,
        )

        assertEquals(targetMessage, res)

        coVerify {
            mockMyMemoryDataSource.getTranslation(
                key = key,
                sourceMessage = sourceMessage,
                sourceLang = sourceLang,
                targetLang = targetLang,
            )
        }
    }

    @Test
    fun givenRepositoryWhenGetTranslationInvokedWithSameKeyThenSameSuggestionIsReturnedAndSourceIsQueriedOnlyOnce() =
        runTest {
            val key = "api_key"
            val sourceMessage = "source message"
            val sourceLang = "en"
            val targetLang = "it"
            val targetMessage = "messaggio di partenza"
            coEvery {
                mockMyMemoryDataSource.getTranslation(
                    key = any(),
                    sourceMessage = any(),
                    sourceLang = any(),
                    targetLang = any(),
                )
            } returns targetMessage
            sut.setProvider(MachineTranslationProvider.MY_MEMORY)
            val res = sut.getTranslation(
                key = key,
                sourceMessage = sourceMessage,
                sourceLang = sourceLang,
                targetLang = targetLang,
            )
            assertEquals(targetMessage, res)

            val res2 = sut.getTranslation(
                key = key,
                sourceMessage = sourceMessage,
                sourceLang = sourceLang,
                targetLang = targetLang,
            )

            assertEquals(targetMessage, res2)

            coVerify(exactly = 1) {
                mockMyMemoryDataSource.getTranslation(
                    key = key,
                    sourceMessage = sourceMessage,
                    sourceLang = sourceLang,
                    targetLang = targetLang,
                )
            }
        }

    @Test
    fun givenRepositoryWhenShareTranslationIsInvokedThenMessageIsShared() = runTest {
        val key = "api_key"
        val sourceMessage = "source message"
        val sourceLang = "en"
        val targetLang = "it"
        val targetMessage = "messaggio di partenza"
        coEvery {
            mockMyMemoryDataSource.contributeTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetMessage = any(),
                targetLang = any(),
            )
        } returns Unit
        sut.setProvider(MachineTranslationProvider.MY_MEMORY)

        sut.shareTranslation(
            key = key,
            sourceMessage = sourceMessage,
            sourceLang = sourceLang,
            targetLang = targetLang,
            targetMessage = targetMessage,
        )

        coVerify {
            mockMyMemoryDataSource.contributeTranslation(
                key = key,
                sourceLang = sourceLang,
                sourceMessage = sourceMessage,
                targetLang = targetLang,
                targetMessage = targetMessage,
            )
        }
    }

    @Test
    fun givenRepositoryWhenGenerateKeyIsInvokedThenKeyIsReturned() = runTest {
        val key = "api_key"
        val username = "user@example.com"
        val password = "test"
        coEvery {
            mockMyMemoryDataSource.generateKey(any(), any())
        } returns key
        sut.setProvider(MachineTranslationProvider.MY_MEMORY)

        val res = sut.generateKey(
            username = username,
            password = password,
        )

        assertEquals(key, res)
        coVerify { mockMyMemoryDataSource.generateKey(username = username, password = password) }
    }

    @Test
    fun givenRepositoryWhenImportTmIsInvokedThenFileIsShared() = runTest {
        val key = "api_key"
        coEvery {
            mockMyMemoryDataSource.import(file = any(), key = any(), private = any())
        } returns Unit
        val file = File.createTempFile("test", ".txt")
        sut.setProvider(MachineTranslationProvider.MY_MEMORY)

        sut.importTm(
            key = key,
            file = file,
            private = true,
        )

        coVerify {
            mockMyMemoryDataSource.import(
                file = file,
                key = key,
                private = true,
            )
        }

        file.delete()
    }
}
