package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultImportSegmentsUseCaseTest {
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultImportSegmentsUseCase(
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @Test
    fun givenUseCaseWhenInvokedThenNewSegmentsAreCreated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(LanguageModel(id = 1, code = "en"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns null
        coEvery { mockSegmentRepository.create(any(), any()) } returns 1
        sut.invoke(
            segments = listOf(SegmentModel(id = 1, key = "key")),
            language = LanguageModel(code = "en", id = 1),
            projectId = 1,
        )
        coVerify { mockSegmentRepository.getByKey("key", 1) }
        coVerify { mockSegmentRepository.create(withArg { assertEquals("key", it.key) }, 1) }
    }

    @Test
    fun givenUseCaseWhenInvokedThenExistingSegmentsAreUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(LanguageModel(id = 1, code = "en"))
        val keySlot = slot<String>()
        coEvery {
            mockSegmentRepository.getByKey(
                capture(keySlot),
                any(),
            )
        } answers { SegmentModel(key = keySlot.captured) }
        coEvery { mockSegmentRepository.update(any()) } returns Unit
        sut.invoke(
            segments = listOf(SegmentModel(id = 1, key = "key")),
            language = LanguageModel(code = "en", id = 1),
            projectId = 1,
        )
        coVerify { mockSegmentRepository.getByKey("key", 1) }
        coVerify { mockSegmentRepository.update(withArg { assertEquals("key", it.key) }) }
    }
}
