package com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultValidateSpellingUseCaseTest {
    private val mockRepository = mockk<SpellCheckRepository>()
    private val sut = DefaultValidateSpellingUseCase(
        repository = mockRepository,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @Test
    fun givenNoErrorsDetectedWhenInvokedThenEmptyMapIsReturned() = runTest {
        coEvery { mockRepository.setLanguage(any()) } returns Unit
        coEvery { mockRepository.check(any()) } returns listOf()
        val res = sut.invoke(listOf(ValidateSpellingUseCase.InputItem(key = "key", message = "A message")), lang = "en")
        assertEquals(0, res.size)
    }

    @Test
    fun givenErrorsDetectedWhenInvokedThenResultMapIsReturned() = runTest {
        coEvery { mockRepository.setLanguage(any()) } returns Unit
        coEvery { mockRepository.check(any()) } returns listOf(
            SpellCheckCorrection(
                indices = 0 until 2,
                value = "a",
                suggestions = listOf("A"),
            ),
        )
        val res = sut.invoke(listOf(ValidateSpellingUseCase.InputItem(key = "key", message = "a message")), lang = "en")
        assertEquals(1, res.size)
        val firstKey = res.keys.first()
        assertEquals("key", firstKey)
        assertEquals(listOf("a"), res[firstKey])
    }
}
