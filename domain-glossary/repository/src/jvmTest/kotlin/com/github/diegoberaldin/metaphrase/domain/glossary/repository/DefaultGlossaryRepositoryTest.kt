package com.github.diegoberaldin.metaphrase.domain.glossary.repository

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.GlossaryTermDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultGlossaryRepositoryTest {
    private val dao = mockk<GlossaryTermDao>()
    private val sut = DefaultGlossaryTermRepository(dao)

    @Test
    fun givenRepositoryWhenCreateIsInvokedThenDataIsCreated() = runTest {
        coEvery { dao.create(any()) } returns 1
        val res = sut.create(GlossaryTermModel(lemma = "test", lang = "en"))
        assertEquals(1, res)
        coVerify { dao.create(withArg { assertEquals("test", it.lemma) }) }
    }

    @Test
    fun givenExistingDataWhenGetByIdIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getById(any()) } returns GlossaryTermModel(id = 1, lemma = "test", lang = "en")
        val res = sut.getById(1)
        assertNotNull(res)
        coVerify { dao.getById(1) }
    }

    @Test
    fun givenExistingDataWhenGetByLemmaIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getBy(any(), any()) } returns GlossaryTermModel(id = 1, lemma = "test", lang = "en")
        val res = sut.get(lemma = "test", lang = "en")
        assertNotNull(res)
        coVerify { dao.getBy(lemma = "test", lang = "en") }
    }

    @Test
    fun givenExistingDataWhenGetAllIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getAll() } returns listOf(GlossaryTermModel(id = 1, lemma = "test", lang = "en"))
        val res = sut.getAll()
        assertEquals(1, res.size)
        coVerify { dao.getAll() }
    }

    @Test
    fun givenExistingDataWhenUpdateIsInvokedThenDataIsUpdated() = runTest {
        coEvery { dao.update(any()) } returns Unit
        sut.update(GlossaryTermModel(id = 1, lemma = "test", lang = "en"))
        coVerify {
            dao.update(
                withArg {
                    assertEquals("test", it.lemma)
                    assertEquals("en", it.lang)
                },
            )
        }
    }

    @Test
    fun givenExistingDataWhenDeleteIsInvokedThenDataIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit
        sut.delete(GlossaryTermModel(id = 1, lemma = "test", lang = "en"))
        coVerify { dao.delete(withArg { assertEquals(1, it.id) }) }
    }

    @Test
    fun givenExistingDataWhenDeleteAllIsInvokedThenDataIsDeleted() = runTest {
        coEvery { dao.deleteAll() } returns Unit
        sut.deleteAll()
        coVerify { dao.deleteAll() }
    }

    @Test
    fun givenExistingDataWhenAreAssociatedIsInvokedThenDataIsQueried() = runTest {
        coEvery { dao.areAssociated(any(), any()) } returns true
        val res = sut.areAssociated(1, 2)
        assertTrue(res)
        coVerify {
            dao.areAssociated(
                sourceId = withArg { assertEquals(1, it) },
                targetId = withArg { assertEquals(2, it) },
            )
        }
    }

    @Test
    fun givenExistingDataWhenGetAssociatedIsInvokedThenDataIsQueried() = runTest {
        coEvery { dao.getAssociated(any(), any()) } returns listOf(GlossaryTermModel(lemma = "test", lang = "it"))
        val res = sut.getAssociated(GlossaryTermModel(id = 1, lemma = "test", lang = "en"), "it")
        assertEquals(1, res.size)
        coVerify {
            dao.getAssociated(
                model = withArg { assertEquals("test", it.lemma) },
                otherLang = withArg { assertEquals("it", it) },
            )
        }
    }

    @Test
    fun givenExistingDataWhenGetAllAssociatedIsInvokedThenDataIsQueried() = runTest {
        coEvery { dao.getAllAssociated(any()) } returns listOf(GlossaryTermModel(lemma = "test", lang = "it"))
        val res = sut.getAllAssociated(GlossaryTermModel(id = 1, lemma = "test", lang = "en"))
        assertEquals(1, res.size)
        coVerify {
            dao.getAllAssociated(
                model = withArg { assertEquals("test", it.lemma) },
            )
        }
    }

    @Test
    fun givenExistingDataWhenAssociateIsInvokedThenDataIsUpdated() = runTest {
        coEvery { dao.associate(any(), any()) } returns Unit
        sut.associate(1, 2)
        coVerify {
            dao.associate(
                sourceId = withArg { assertEquals(1, it) },
                targetId = withArg { assertEquals(2, it) },
            )
        }
    }

    @Test
    fun givenExistingDataWhenDisassociateIsInvokedThenDataIsUpdated() = runTest {
        coEvery { dao.disassociate(any(), any()) } returns Unit
        sut.disassociate(1, 2)
        coVerify {
            dao.disassociate(
                sourceId = withArg { assertEquals(1, it) },
                targetId = withArg { assertEquals(2, it) },
            )
        }
    }

    @Test
    fun givenExistingDataWhenIsStillReferencedIsInvokedThenDataIsQueried() = runTest {
        coEvery { dao.isStillReferenced(any()) } returns true
        sut.isStillReferenced(1)
        coVerify {
            dao.isStillReferenced(withArg { assertEquals(1, it) })
        }
    }
}
