package com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.persistence.DefaultAppDatabase
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultMemoryEntryDaoTest {

    private lateinit var sut: DefaultMemoryEntryDao

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".db",
        )
        val appDb = DefaultAppDatabase(
            filename = "test",
            fileManager = MockFileManager,
        )
        sut = appDb.memoryEntryDao()
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyDatabaseWhenGetInvokedThenEmptyListIsReturned() = runTest {
        val res = sut.getEntries("en")
        assertEquals(0, res.size)
    }

    @Test
    fun givenDatabaseWhenCreateInvokedThenEntityIsInserted() = runTest {
        val model = TranslationMemoryEntryModel(identifier = "key", sourceLang = "en")
        val id = sut.create(model)
        assertTrue(id > 0)
    }

    @Test
    fun givenDatabaseWhenGetInvokedThenListIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
        )
        sut.create(model)

        val res = sut.getEntries("en", targetLang = "it", search = "")

        assertEquals(1, res.size)
        val entry = res.first()
        assertEquals("test", entry.sourceText)
        assertEquals("en", entry.sourceLang)
        assertEquals("prova", entry.targetText)
        assertEquals("it", entry.targetLang)
    }

    @Test
    fun givenDatabaseWhenDeleteInvokedThenEntityIsDeleted() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
        )
        val id = sut.create(model)
        val resBefore = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resBefore.size)

        sut.delete(model.copy(id = id))

        val resAfter = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(0, resAfter.size)
    }

    @Test
    fun givenDatabaseWhenDeleteAllInvokedThenEntityIsDeleted() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
        )
        sut.create(model)
        val resBefore = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resBefore.size)

        sut.deleteAll(origin = "")

        val resAfter = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(0, resAfter.size)
    }

    @Test
    fun givenDatabaseWhenUpdateSourceTextInvokedThenEntityIsUpdated() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetText = "prova",
            targetLang = "it",
        )
        val id = sut.create(model)
        val resBefore = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resBefore.size)
        val entryBefore = resBefore.first()
        assertEquals("test", entryBefore.sourceText)
        assertEquals("en", entryBefore.sourceLang)
        assertEquals("prova", entryBefore.targetText)
        assertEquals("it", entryBefore.targetLang)

        sut.update(model.copy(id = id, sourceText = "updated"))

        val resAfter = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resAfter.size)
        val entryAfter = resAfter.first()
        assertEquals("updated", entryAfter.sourceText)
        assertEquals("en", entryAfter.sourceLang)
        assertEquals("prova", entryAfter.targetText)
        assertEquals("it", entryAfter.targetLang)
    }

    @Test
    fun givenDatabaseWhenUpdateTargetTextInvokedThenEntityIsUpdated() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetText = "prova",
            targetLang = "it",
        )
        val id = sut.create(model)
        val resBefore = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resBefore.size)
        val entryBefore = resBefore.first()
        assertEquals("test", entryBefore.sourceText)
        assertEquals("en", entryBefore.sourceLang)
        assertEquals("prova", entryBefore.targetText)
        assertEquals("it", entryBefore.targetLang)

        sut.update(model.copy(id = id, targetText = "aggiornato"))

        val resAfter = sut.getEntries("en", targetLang = "it", search = "")
        assertEquals(1, resAfter.size)
        val entryAfter = resAfter.first()
        assertEquals("test", entryAfter.sourceText)
        assertEquals("en", entryAfter.sourceLang)
        assertEquals("aggiornato", entryAfter.targetText)
        assertEquals("it", entryAfter.targetLang)
    }

    @Test
    fun givenDatabaseWhenGetByIdentifierInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getByIdentifier(identifier = "key", origin = "test_origin", sourceLang = "en", targetLang = "it")
        assertNotNull(res)
        assertEquals("test", res.sourceText)
        assertEquals("en", res.sourceLang)
        assertEquals("prova", res.targetText)
        assertEquals("it", res.targetLang)
    }

    @Test
    fun givenDatabaseWhenGetByIdentifierNonExistingInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res =
            sut.getByIdentifier(identifier = "key_n", origin = "test_origin", sourceLang = "en", targetLang = "it")
        assertNull(res)
    }

    @Test
    fun givenDatabaseWhenGetByIdentifierNonExistingOriginInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getByIdentifier(identifier = "key", origin = "origin_n", sourceLang = "en", targetLang = "it")
        assertNull(res)
    }

    @Test
    fun givenDatabaseWhenGetByIdentifierNonExistingTargetLangInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getByIdentifier(identifier = "key", origin = "test_origin", sourceLang = "en", targetLang = "es")
        assertNull(res)
    }

    @Test
    fun givenDatabaseWhenGetLanguageCodesInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getLanguageCodes()
        assertEquals(2, res.size)
        assertContains(res, "en")
        assertContains(res, "it")
    }

    @Test
    fun givenDatabaseWhenGetTargetMessagesInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getTargetMessage(lang = "it", key = "key")
        assertNotNull(res)
        assertEquals("prova", res.targetText)
        assertEquals("it", res.targetLang)
    }

    @Test
    fun givenDatabaseWhenGetTargetMessagesInvokedNotExistingIdentifierThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getTargetMessage(lang = "it", key = "key_n")
        assertNull(res)
    }

    @Test
    fun givenDatabaseWhenGetTargetMessagesInvokedNotExistingLangThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getTargetMessage(lang = "es", key = "key")
        assertNull(res)
    }

    @Test
    fun givenDatabaseWhenSearchInvokedThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getEntries(
            sourceLang = "en",
            targetLang = "it",
            search = "",
        )

        assertEquals(1, res.size)
    }

    @Test
    fun givenDatabaseWhenSearchInvokedNonExistingLangThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getEntries(
            sourceLang = "en",
            targetLang = "es",
            search = "",
        )

        assertEquals(0, res.size)
    }

    @Test
    fun givenDatabaseWhenSearchInvokedNonExistingSearchThenResultIsReturned() = runTest {
        val model = TranslationMemoryEntryModel(
            identifier = "key",
            sourceLang = "en",
            sourceText = "test",
            targetLang = "it",
            targetText = "prova",
            origin = "test_origin",
        )
        sut.create(model)

        val res = sut.getEntries(
            sourceLang = "en",
            targetLang = "es",
            search = "-",
        )

        assertEquals(0, res.size)
    }

    @Test
    fun givenDatabaseWhenSearchQueryInvokedThenResultIsReturned() = runTest {
        val model1 = TranslationMemoryEntryModel(
            identifier = "key_1",
            sourceLang = "en",
            sourceText = "test 1",
            targetLang = "it",
            targetText = "prova 1",
            origin = "test_origin",
        )
        val model2 = TranslationMemoryEntryModel(
            identifier = "key_2",
            sourceLang = "en",
            sourceText = "test 2",
            targetLang = "it",
            targetText = "prova 2",
            origin = "test_origin",
        )
        sut.create(model1)
        sut.create(model2)

        val res = sut.getEntries(
            sourceLang = "en",
            targetLang = "it",
            search = "1",
        )

        assertEquals(1, res.size)
    }
}
