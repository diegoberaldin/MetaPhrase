package com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling

import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.UserDefinedWordsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultSpellingTest {
    private val mockUserDefinedWordsRepository = mockk<UserDefinedWordsRepository>()
    private val sut = DefaultSpelling(
        userDefinedWordsRepository = mockUserDefinedWordsRepository,
    )

    @Test
    fun givenSpellingWhenLanguageNotSetThenIsNotInitialized() {
        val res = sut.isInitialized
        assertFalse(res)
    }

    @Test
    fun givenSpellingWhenLanguageSetThenIsInitialized() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns emptyList()
        sut.setLanguage("en")
        val res = sut.isInitialized
        assertTrue(res)
    }

    @Test
    fun givenSpellingWhenLanguageSetToUnknownThenIsInitialized() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns emptyList()
        sut.setLanguage("xxx")
        val res = sut.isInitialized
        assertFalse(res)
    }

    @Test
    fun givenSpellingWithLanguageSetWhenUserDefinedWordAddedThenRepositoryIsCalled() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns emptyList()
        coEvery { mockUserDefinedWordsRepository.add(any(), any()) } returns Unit
        sut.setLanguage("en")
        sut.addUserDefinedWord("word")
        coVerify { mockUserDefinedWordsRepository.add("word", "en") }
    }

    @Test
    fun givenSpellingWithLanguageSetWhenCheckWordIsInvokedThenListIsReturned() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns emptyList()
        sut.setLanguage("en")
        val res = sut.check("chieck")
        assertTrue(res.isNotEmpty())
    }

    @Test
    fun givenSpellingWithLanguageSetAndIgnoredWordWhenCheckWordIsInvokedThenEmptyListIsReturned() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns listOf("chieck")
        sut.setLanguage("en")
        val res = sut.check("chieck")
        assertTrue(res.isEmpty())
    }

    @Test
    fun givenSpellingWithLanguageSetWhenCheckSentenceIsInvokedThenListIsReturned() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns emptyList()
        sut.setLanguage("en")
        val res = sut.checkSentence("A message to chieck")
        assertTrue(res.isNotEmpty())
    }

    @Test
    fun givenSpellingWithLanguageSetAndIgnoredWordWhenCheckSentenceIsInvokedThenEmptyListIsReturned() = runTest {
        coEvery { mockUserDefinedWordsRepository.getAll(any()) } returns listOf("chieck")
        sut.setLanguage("en")
        val res = sut.checkSentence("A message to chieck")
        assertTrue(res.isEmpty())
    }
}
