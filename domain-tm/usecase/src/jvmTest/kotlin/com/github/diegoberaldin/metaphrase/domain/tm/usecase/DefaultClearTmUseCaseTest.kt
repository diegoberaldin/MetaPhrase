package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DefaultClearTmUseCaseTest {
    private val mockRepository = mockk<MemoryEntryRepository>()
    private val sut = DefaultClearTmUseCase(
        memoryEntryRepository = mockRepository,
    )

    @Test
    fun givenUseCaseWhenInvokedThenMemoryIsCleared() = runTest {
        coEvery { mockRepository.deleteAll() } returns Unit
        sut.invoke()
        coVerify { mockRepository.deleteAll() }
    }
}
