package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultImportGlossaryUseCaseTest {
    private val mockRepository = mockk<GlossaryTermRepository>()
    private val sut = DefaultImportGlossaryUseCase(
        repository = mockRepository,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("glossary", ".csv")
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenCsvGlossaryWhenInvokedThenTermsAreAddedAndAssociated() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
en, it
test, prova
                """.trimIndent(),
            )
        }
        coEvery { mockRepository.get(any(), any()) } returns null
        var id = 0
        coEvery { mockRepository.create(any()) } answers { id++ }
        coEvery { mockRepository.areAssociated(any(), any()) } returns false
        coEvery { mockRepository.associate(any(), any()) } returns Unit

        sut.invoke(path)

        coVerify {
            mockRepository.get("test", "en")
            mockRepository.get("prova", "it")
            mockRepository.create(
                withArg {
                    assertEquals("test", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.areAssociated(0, 1)
            mockRepository.associate(0, 1)
        }
    }

    @Test
    fun givenCsvGlossaryWithSourceSynonymsWhenInvokedThenTermsAreAddedAndAssociated() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
en, it
"test1, test2", prova
                """.trimIndent(),
            )
        }
        coEvery { mockRepository.get(any(), any()) } returns null
        var id = 0
        coEvery { mockRepository.create(any()) } answers { id++ }
        coEvery { mockRepository.areAssociated(any(), any()) } returns false
        coEvery { mockRepository.associate(any(), any()) } returns Unit

        sut.invoke(path)

        coVerify {
            mockRepository.get("test1", "en")
            mockRepository.get("test2", "en")
            mockRepository.get("prova", "it")
            mockRepository.create(
                withArg {
                    assertEquals("test1", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("test2", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.areAssociated(0, 2)
            mockRepository.areAssociated(1, 2)
            mockRepository.associate(0, 2)
            mockRepository.associate(1, 2)
        }
    }

    @Test
    fun givenCsvGlossaryWithTargetSynonymsWhenInvokedThenTermsAreAddedAndAssociated() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
en, it
test, "prova1,prova2"
                """.trimIndent(),
            )
        }
        coEvery { mockRepository.get(any(), any()) } returns null
        var id = 0
        coEvery { mockRepository.create(any()) } answers { id++ }
        coEvery { mockRepository.areAssociated(any(), any()) } returns false
        coEvery { mockRepository.associate(any(), any()) } returns Unit

        sut.invoke(path)

        coVerify {
            mockRepository.get("test", "en")
            mockRepository.get("prova1", "it")
            mockRepository.get("prova2", "it")
            mockRepository.create(
                withArg {
                    assertEquals("test", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova1", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova2", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.areAssociated(0, 1)
            mockRepository.areAssociated(0, 2)
            mockRepository.associate(0, 1)
            mockRepository.associate(0, 2)
        }
    }

    @Test
    fun givenCsvGlossaryWithSourceAndTargetSynonymsWhenInvokedThenTermsAreAddedAndAssociated() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
en, it
"test1,test2", "prova1,prova2"
                """.trimIndent(),
            )
        }
        coEvery { mockRepository.get(any(), any()) } returns null
        var id = 0
        coEvery { mockRepository.create(any()) } answers { id++ }
        coEvery { mockRepository.areAssociated(any(), any()) } returns false
        coEvery { mockRepository.associate(any(), any()) } returns Unit

        sut.invoke(path)

        coVerify {
            mockRepository.get("test1", "en")
            mockRepository.get("test2", "en")
            mockRepository.get("prova1", "it")
            mockRepository.get("prova2", "it")
            mockRepository.create(
                withArg {
                    assertEquals("test1", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("test2", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova1", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.create(
                withArg {
                    assertEquals("prova2", it.lemma)
                    assertEquals("it", it.lang)
                },
            )
            mockRepository.areAssociated(0, 2)
            mockRepository.areAssociated(1, 2)
            mockRepository.areAssociated(0, 3)
            mockRepository.areAssociated(1, 3)
            mockRepository.associate(0, 2)
            mockRepository.associate(0, 3)
            mockRepository.associate(1, 2)
            mockRepository.associate(1, 3)
        }
    }
}
