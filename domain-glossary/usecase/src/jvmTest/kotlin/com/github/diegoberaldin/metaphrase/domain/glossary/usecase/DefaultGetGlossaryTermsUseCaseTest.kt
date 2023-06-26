package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.Spelling
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultGetGlossaryTermsUseCaseTest {
    private val mockRepository = mockk<GlossaryTermRepository>()
    private val mockSpelling = mockk<Spelling>()
    private val sut = DefaultGetGlossaryTermsUseCase(
        repository = mockRepository,
        dispatchers = MockCoroutineDispatcherProvider,
        spelling = mockSpelling,
    )

    @Test
    fun givenEmptyDatabaseWhenInvokedThenEmptyListIsReturned() = runTest {
        val message = "Message to analyze"
        coEvery { mockSpelling.setLanguage(any()) } returns Unit
        coEvery { mockSpelling.getLemmata(any()) } returns listOf("message", "to", "analyze")
        coEvery { mockRepository.get(any(), any()) } returns null

        val res = sut.invoke(message = message, lang = "en")
        assertEquals(0, res.size)

        coVerify {
            mockSpelling.setLanguage("en")
            mockSpelling.getLemmata(message)
            mockRepository.get("message", "en")
            mockRepository.get("to", "en")
            mockRepository.get("analyze", "en")
        }
    }

    @Test
    fun givenNotEmptyDatabaseWhenInvokedThenTermListIsReturned() = runTest {
        val message = "Message to analyze"
        coEvery { mockSpelling.setLanguage(any()) } returns Unit
        coEvery { mockSpelling.getLemmata(any()) } returns listOf("message", "to", "analyze")
        val slot = slot<String>()
        coEvery { mockRepository.get(capture(slot), any()) } answers {
            if (slot.captured == "message") {
                GlossaryTermModel(lemma = "message")
            } else {
                null
            }
        }

        val res = sut.invoke(message = message, lang = "en")
        assertEquals(1, res.size)

        coVerify {
            mockSpelling.setLanguage("en")
            mockSpelling.getLemmata(message)
            mockRepository.get("message", "en")
            mockRepository.get("to", "en")
            mockRepository.get("analyze", "en")
        }
    }
}
