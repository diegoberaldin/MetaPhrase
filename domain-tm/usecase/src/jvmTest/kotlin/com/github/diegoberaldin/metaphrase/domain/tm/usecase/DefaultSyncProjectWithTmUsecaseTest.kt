package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DefaultSyncProjectWithTmUsecaseTest {
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockMemoryEntryRepository = mockk<MemoryEntryRepository>()
    private val sut = DefaultSyncProjectWithTmUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
        projectRepository = mockProjectRepository,
        segmentRepository = mockSegmentRepository,
        languageRepository = mockLanguageRepository,
        memoryEntryRepository = mockMemoryEntryRepository,
    )

    @Test
    fun givenUseCaseWhenInvokedThenAllProjectEntriesAreCreatedIntoTm() = runTest {
        coEvery { mockProjectRepository.getById(any()) } returns ProjectModel(name = "test")
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 0, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(id = 0, code = "en", isBase = true),
            LanguageModel(id = 1, code = "it"),
        )
        coEvery { mockSegmentRepository.getAll(0) } returns listOf(SegmentModel(key = "key", text = "test"))
        coEvery { mockSegmentRepository.getByKey("key", 1) } returns SegmentModel(key = "key", text = "prova")
        coEvery { mockMemoryEntryRepository.deleteAll(any()) } returns Unit
        coEvery { mockMemoryEntryRepository.getByIdentifier(any(), any(), any(), any()) } returns null
        coEvery { mockMemoryEntryRepository.create(any()) } returns 1

        sut.invoke(0)

        coVerify {
            mockMemoryEntryRepository.deleteAll("test")
            mockMemoryEntryRepository.create(
                withArg {
                    assertEquals("en", it.sourceLang)
                    assertEquals("it", it.targetLang)
                    assertEquals("test", it.sourceText)
                    assertEquals("prova", it.targetText)
                },
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedThenExitsingProjectEntriesAreUpdatedIntoTm() = runTest {
        coEvery { mockProjectRepository.getById(any()) } returns ProjectModel(name = "test")
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 0, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(id = 0, code = "en", isBase = true),
            LanguageModel(id = 1, code = "it"),
        )
        coEvery { mockSegmentRepository.getAll(0) } returns listOf(SegmentModel(key = "key", text = "test"))
        coEvery { mockSegmentRepository.getByKey("key", 1) } returns SegmentModel(key = "key", text = "prova")
        coEvery { mockMemoryEntryRepository.deleteAll(any()) } returns Unit
        coEvery {
            mockMemoryEntryRepository.getByIdentifier(
                identifier = any(),
                origin = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns TranslationMemoryEntryModel()
        coEvery { mockMemoryEntryRepository.update(any()) } returns Unit

        sut.invoke(0)

        coVerify {
            mockMemoryEntryRepository.deleteAll("test")
            mockMemoryEntryRepository.update(
                withArg {
                    assertEquals("test", it.sourceText)
                    assertEquals("prova", it.targetText)
                },
            )
        }
    }
}
