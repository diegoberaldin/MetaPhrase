package com.github.diegoberaldin.metaphrase.domain.language.repository

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.LanguageDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultLanguageRepositoryTest {
    private val dao = mockk<LanguageDao>()
    private val sut = DefaultLanguageRepository(dao)

    @Test
    fun givenRepositoryWhenCreateInvokedThenItemIsCreated() = runTest {
        val languageId = 2
        coEvery { dao.create(any(), any()) } returns languageId
        val res = sut.create(LanguageModel(code = "en"), 0)
        assertEquals(languageId, res)
        coVerify { dao.create(any(), 0) }
    }

    @Test
    fun givenRepositoryWhenGetDefaultIsInvokedThenDataIsRetrieved() = runTest {
        val res = sut.getDefaultLanguages()
        assertFalse(res.isEmpty())
    }

    @Test
    fun givenEmptyDatabaseWhenGetAllIsInvokedThenNoDataIsRetrieved() = runTest {
        coEvery { dao.getAll(any()) } returns emptyList()
        val res = sut.getAll(0)
        assertEquals(0, res.size)
        coVerify { dao.getAll(0) }
    }

    @Test
    fun givenExistingDataWhenGetAllIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getAll(any()) } returns listOf(LanguageModel(code = "en"))
        val res = sut.getAll(0)
        assertEquals(1, res.size)
        coVerify { dao.getAll(0) }
    }

    @Test
    fun givenExistingDataWhenGetBaseIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val res = sut.getBase(0)
        assertNotNull(res)
        assertTrue(res.isBase)
        coVerify { dao.getBase(0) }
    }

    @Test
    fun givenExistingDataWhenObserveAllIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getAll(any()) } returns listOf(LanguageModel(code = "en"))
        val res = sut.observeAll(0).first()
        assertEquals(1, res.size)
        coVerify { dao.getAll(0) }
    }

    @Test
    fun givenExistingDataWhenGetByIdIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getById(any()) } returns LanguageModel(code = "en")
        val res = sut.getById(1)
        assertNotNull(res)
        coVerify { dao.getById(1) }
    }

    @Test
    fun givenExistingDataWhenGetByCodeIsInvokedThenDataIsRetrieved() = runTest {
        coEvery { dao.getByCode(any(), any()) } returns LanguageModel(code = "en")
        val res = sut.getByCode("en", 0)
        assertNotNull(res)
        coVerify { dao.getByCode("en", 0) }
    }

    @Test
    fun givenRepositoryWhenDeleteIsInvokedThenDataIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit
        sut.delete(LanguageModel(code = "en"))
        coVerify {
            dao.delete(withArg { assertEquals("en", it.code) })
        }
    }

    @Test
    fun givenRepositoryWhenUpdateIsInvokedThenDataIsUpdated() = runTest {
        coEvery { dao.update(any()) } returns Unit
        sut.update(LanguageModel(code = "en"))
        coVerify {
            dao.update(withArg { assertEquals("en", it.code) })
        }
    }
}
