package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.domain.spellcheck.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.Spelling
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultSpellCheckRepositoryTest {
    private val mockSpelling = mockk<Spelling>()
    private val sut = DefaultSpellCheckRepository(
        spelling = mockSpelling,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @Test
    fun givenRepositoryWhenLanguageSetThenSpellcheckIsConfigured() = runTest {
        coEvery { mockSpelling.setLanguage(any()) } returns Unit
        sut.setLanguage("en")
        coVerify { mockSpelling.setLanguage("en") }
    }

    @Test
    fun givenRepositoryWithLanguageNotSetWhenCheckInvokedThenReturnsEmptyList() = runTest {
        every { mockSpelling.isInitialized } returns false
        val res = sut.check("A message to chieck")
        assertEquals(0, res.size)
    }

    @Test
    fun givenRepositoryWithLanguageSetWhenCheckInvokedThenReturnsList() = runTest {
        every { mockSpelling.isInitialized } returns true
        coEvery { mockSpelling.checkSentence(any()) } returns listOf(
            SpellCheckCorrection(
                indices = 13 until 19,
                value = "chieck",
                suggestions = listOf("check"),
            ),
        )
        val res = sut.check("A message to chieck")
        assertEquals(1, res.size)
        val suggestion = res.first()
        assertEquals("chieck", suggestion.value)
    }

    @Test
    fun givenRepositoryWithLanguageSetWhenWordAddedInvokedThenSpellingIsCalled() = runTest {
        every { mockSpelling.isInitialized } returns true
        coEvery { mockSpelling.addUserDefinedWord(any()) } returns Unit
        sut.addUserDefineWord("word")
        coVerify { mockSpelling.addUserDefinedWord("word") }
    }

    @Test
    fun givenRepositoryWithNoLanguageSetWhenWordAddedInvokedThenSpellingIsNotCalled() = runTest {
        every { mockSpelling.isInitialized } returns false
        coEvery { mockSpelling.addUserDefinedWord(any()) } returns Unit
        sut.addUserDefineWord("word")
        coVerify(inverse = true) { mockSpelling.addUserDefinedWord(any()) }
    }
}
