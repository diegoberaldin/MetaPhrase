package com.github.diegoberaldin.metaphrase.domain.tm.repository

import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.MemoryEntryDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultMemoryEntryRepositoryTest {
    private val dao = mockk<MemoryEntryDao>()
    private val sut = DefaultMemoryEntryRepository(
        dao = dao,
    )

    @Test
    fun givenRepositoryWhenGetEntriesThenDatabaseIsQueried() = runTest {
        coEvery { dao.getEntries(any()) } returns emptyList()
        sut.getEntries(sourceLang = "en")
        coVerify { dao.getEntries("en") }
    }

    @Test
    fun givenRepositoryWhenSearchEntriesThenDatabaseIsQueried() = runTest {
        coEvery { dao.getEntries(sourceLang = any(), targetLang = any(), search = any()) } returns emptyList()
        sut.getEntries(sourceLang = "en", "it", "")
        coVerify { dao.getEntries(sourceLang = "en", targetLang = "it", search = "") }
    }

    @Test
    fun givenRepositoryWhenGetLanguageCodesThenDatabaseIsQueried() = runTest {
        coEvery { dao.getLanguageCodes() } returns emptyList()
        sut.getLanguageCodes()
        coVerify { dao.getLanguageCodes() }
    }

    @Test
    fun givenRepositoryWhenGetTranslationThenDatabaseIsQueried() = runTest {
        coEvery { dao.getTargetMessage(any(), any()) } returns null
        sut.getTranslation(lang = "it", key = "key")
        coVerify { dao.getTargetMessage(lang = "it", key = "key") }
    }

    @Test
    fun givenRepositoryWhenGetByIdentifierThenDatabaseIsQueried() = runTest {
        coEvery {
            dao.getByIdentifier(
                identifier = any(),
                origin = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns null
        sut.getByIdentifier(identifier = "key", origin = "any", sourceLang = "en", targetLang = "it")
        coVerify { dao.getByIdentifier(identifier = "key", origin = "any", sourceLang = "en", targetLang = "it") }
    }

    @Test
    fun givenRepositoryWhenCreateThenItemIsCreated() = runTest {
        coEvery { dao.create(any()) } returns 0
        sut.create(TranslationMemoryEntryModel(identifier = "key"))
        coVerify { dao.create(withArg { assertEquals("key", it.identifier) }) }
    }

    @Test
    fun givenRepositoryWhenUpdateThenItemIsUpdated() = runTest {
        coEvery { dao.update(any()) } returns Unit
        sut.update(TranslationMemoryEntryModel(identifier = "key"))
        coVerify { dao.update(withArg { assertEquals("key", it.identifier) }) }
    }

    @Test
    fun givenRepositoryWhenDeleteThenItemIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit
        sut.delete(TranslationMemoryEntryModel(identifier = "key"))
        coVerify { dao.delete(withArg { assertEquals("key", it.identifier) }) }
    }

    @Test
    fun givenRepositoryWhenDeleteAllThenItemsAreDeleted() = runTest {
        coEvery { dao.deleteAll(any()) } returns Unit
        sut.deleteAll("origin")
        coVerify { dao.deleteAll("origin") }
    }
}
