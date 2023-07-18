package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultImportResourcesV2UseCaseTest {
    private val mockBaseUseCase = mockk<ImportResourcesUseCase>()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultImportResourcesV2UseCase(
        baseUseCase = mockBaseUseCase,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
    )

    @Test
    fun givenUseCaseWhenInvokedForAndroidThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.ANDROID_XML

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForIosThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.IOS_STRINGS

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForResxThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.RESX

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPoThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.PO

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForJsonThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.JSON

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForArbThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.ARB

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }

    @Test
    fun givenUseCaseWhenInvokedForPropertiesThenBaseUseCaseIsInvokedAccordingly() = runTest {
        val segments = listOf(SegmentModel(key = "key"))
        coEvery { mockBaseUseCase.invoke(any(), any()) } returns segments
        val languageCodeSlot = slot<String>()
        coEvery { mockLanguageRepository.getByCode(capture(languageCodeSlot), any()) } answers {
            val lang = languageCodeSlot.captured
            val id = when (lang) {
                "en" -> 1
                "it" -> 2
                else -> 0
            }
            LanguageModel(id = id, code = lang)
        }
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
        val resourceType = ResourceFileType.PROPERTIES

        sut.invoke(
            projectId = 0,
            paths = mapOf(
                "en" to "path_en",
                "it" to "path_it",
            ),
            type = resourceType,
        )

        coVerify {
            mockBaseUseCase.invoke("path_en", resourceType)
            mockBaseUseCase.invoke("path_it", resourceType)
        }
        coVerify {
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 1,
            )
            mockSegmentRepository.createBatch(
                models = withArg {
                    assertEquals(1, it.size)
                    assertEquals("key", it.first().key)
                },
                languageId = 2,
            )
        }
    }
}
