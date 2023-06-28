package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultExportGlossaryUseCaseTest {
    private val mockRepository = mockk<GlossaryTermRepository>()
    private val sut = DefaultExportGlossaryUseCase(
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
    fun givenGlossaryWhenInvokedThenCorrectOutputIsWritten() = runTest {
        val term1 = GlossaryTermModel(0, "test", "en")
        val term3 = GlossaryTermModel(2, "prova", "it")
        coEvery { mockRepository.getAll() } returns listOf(term1, term3)
        val idSlot = slot<Int>()
        coEvery { mockRepository.getById(capture(idSlot)) } answers {
            when (idSlot.captured) {
                0 -> term1
                2 -> term3
                else -> null
            }
        }
        val termSlot = slot<GlossaryTermModel>()
        coEvery { mockRepository.getAllAssociated(capture(termSlot)) } answers {
            when (termSlot.captured.id) {
                0 -> listOf(term3)
                1 -> listOf(term3)
                2 -> listOf(term1)
                3 -> listOf(term1)
                else -> emptyList()
            }
        }

        val path = MockFileManager.getFilePath()
        sut.invoke(path)
        FileReader(File(path)).use { reader ->
            val content = reader.readText()
            assertEquals(
                """
en,it
test,prova

                """.trimIndent().replace("\n", "\r\n"),
                content,
            )
        }
    }

    @Test
    fun givenGlossaryWithSourceSynonymsWhenInvokedThenCorrectOutputIsWritten() = runTest {
        val term1 = GlossaryTermModel(0, "test1", "en")
        val term2 = GlossaryTermModel(1, "test2", "en")
        val term3 = GlossaryTermModel(2, "prova1", "it")
        coEvery { mockRepository.getAll() } returns listOf(term1, term2, term3)
        val idSlot = slot<Int>()
        coEvery { mockRepository.getById(capture(idSlot)) } answers {
            when (idSlot.captured) {
                0 -> term1
                1 -> term2
                2 -> term3
                else -> null
            }
        }
        val termSlot = slot<GlossaryTermModel>()
        coEvery { mockRepository.getAllAssociated(capture(termSlot)) } answers {
            when (termSlot.captured.id) {
                0 -> listOf(term3)
                1 -> listOf(term3)
                2 -> listOf(term1, term2)
                3 -> listOf(term1, term2)
                else -> emptyList()
            }
        }

        val path = MockFileManager.getFilePath()
        sut.invoke(path)
        FileReader(File(path)).use { reader ->
            val content = reader.readText()
            assertEquals(
                """
en,it
"test1,test2",prova1

                """.trimIndent().replace("\n", "\r\n"),
                content,
            )
        }
    }

    @Test
    fun givenGlossaryWithTargetSynonymsWhenInvokedThenCorrectOutputIsWritten() = runTest {
        val term1 = GlossaryTermModel(0, "test1", "en")
        val term3 = GlossaryTermModel(2, "prova1", "it")
        val term4 = GlossaryTermModel(3, "prova2", "it")
        coEvery { mockRepository.getAll() } returns listOf(term1, term3, term4)
        val idSlot = slot<Int>()
        coEvery { mockRepository.getById(capture(idSlot)) } answers {
            when (idSlot.captured) {
                0 -> term1
                2 -> term3
                3 -> term4
                else -> null
            }
        }
        val termSlot = slot<GlossaryTermModel>()
        coEvery { mockRepository.getAllAssociated(capture(termSlot)) } answers {
            when (termSlot.captured.id) {
                0 -> listOf(term3, term4)
                1 -> listOf(term3, term4)
                2 -> listOf(term1)
                3 -> listOf(term1)
                else -> emptyList()
            }
        }

        val path = MockFileManager.getFilePath()
        sut.invoke(path)
        FileReader(File(path)).use { reader ->
            val content = reader.readText()
            assertEquals(
                """
en,it
test1,"prova1,prova2"

                """.trimIndent().replace("\n", "\r\n"),
                content,
            )
        }
    }

    @Test
    fun givenGlossaryWithSourceAndTargetSynonymsWhenInvokedThenCorrectOutputIsWritten() = runTest {
        val term1 = GlossaryTermModel(0, "test1", "en")
        val term2 = GlossaryTermModel(1, "test2", "en")
        val term3 = GlossaryTermModel(2, "prova1", "it")
        val term4 = GlossaryTermModel(3, "prova2", "it")
        coEvery { mockRepository.getAll() } returns listOf(term1, term2, term3, term4)
        val idSlot = slot<Int>()
        coEvery { mockRepository.getById(capture(idSlot)) } answers {
            when (idSlot.captured) {
                0 -> term1
                1 -> term2
                2 -> term3
                3 -> term4
                else -> null
            }
        }
        val termSlot = slot<GlossaryTermModel>()
        coEvery { mockRepository.getAllAssociated(capture(termSlot)) } answers {
            when (termSlot.captured.id) {
                0 -> listOf(term3, term4)
                1 -> listOf(term3, term4)
                2 -> listOf(term1, term2)
                3 -> listOf(term1, term2)
                else -> emptyList()
            }
        }

        val path = MockFileManager.getFilePath()
        sut.invoke(path)
        FileReader(File(path)).use { reader ->
            val content = reader.readText()
            assertEquals(
                """
en,it
"test1,test2","prova1,prova2"

                """.trimIndent().replace("\n", "\r\n"),
                content,
            )
        }
    }
}
