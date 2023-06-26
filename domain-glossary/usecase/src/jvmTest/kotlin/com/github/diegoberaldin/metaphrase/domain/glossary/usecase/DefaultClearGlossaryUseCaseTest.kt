package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DefaultClearGlossaryUseCaseTest {
    private val mockRepository = mockk<GlossaryTermRepository>()
    private val sut = DefaultClearGlossaryUseCase(repository = mockRepository)

    @Test
    fun givenUseCaseWhenInvokedThenGlossaryIsCleared() = runTest {
        coEvery { mockRepository.deleteAll() } returns Unit
        sut.invoke()
        coVerify { mockRepository.deleteAll() }
    }
}
