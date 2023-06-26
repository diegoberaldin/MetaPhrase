package com.github.diegoberaldin.metaphrase.domain.language.usecase

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.FlagsRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageNameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultGetCompleteLanguageUseCaseTest {

    private val mockLanguageNameRepository = mockk<LanguageNameRepository>()
    private val mockFlagsRepository = mockk<FlagsRepository>()
    private val sut = DefaultGetCompleteLanguageUseCase(
        languageNameRepository = mockLanguageNameRepository,
        flagsRepository = mockFlagsRepository,
    )

    @Test
    fun givenUseCaseWhenInvokedForEnglishLanguageThenNameAndFlagsAreRetrieved() {
        val flag = "\uD83C\uDDEC\uD83C\uDDE7"
        val name = "English"
        coEvery { mockLanguageNameRepository.getName(any()) } returns name
        coEvery { mockFlagsRepository.getFlag(any()) } returns flag
        val res = sut.invoke(LanguageModel(code = "en"))
        assertEquals("$flag $name", res.name)
        coVerify { mockLanguageNameRepository.getName("en") }
        coVerify { mockFlagsRepository.getFlag("en") }
    }

    @Test
    fun givenUseCaseWhenInvokedForItalianLanguageThenNameAndFlagsAreRetrieved() {
        val flag = "\uD83C\uDDEE\uD83C\uDDF9"
        val name = "Italiano"
        coEvery { mockLanguageNameRepository.getName(any()) } returns name
        coEvery { mockFlagsRepository.getFlag(any()) } returns flag
        val res = sut.invoke(LanguageModel(code = "it"))
        assertEquals("$flag $name", res.name)
        coVerify { mockLanguageNameRepository.getName("it") }
        coVerify { mockFlagsRepository.getFlag("it") }
    }
}
