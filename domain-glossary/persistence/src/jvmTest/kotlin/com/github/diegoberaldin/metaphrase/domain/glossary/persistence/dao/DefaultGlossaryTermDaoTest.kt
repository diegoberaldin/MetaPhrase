package com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.persistence.AppDatabase
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultGlossaryTermDaoTest {
    private lateinit var appDb: AppDatabase
    private lateinit var sut: DefaultGlossaryTermDao

    @BeforeTest
    fun setup() {
        MockFileManager.setup(
            name = "test",
            extension = ".db",
        )
        appDb = AppDatabase(
            filename = "test",
            fileManager = MockFileManager,
        )
        sut = appDb.glossaryTermDao()
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyDatabaseWhenTermInsertedThenRowIsCreated() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)
        assertTrue(id > 0)
    }

    @Test
    fun givenEmptyDatabaseWhenGetAllIsInvokedThenNoTermsAreRetrieved() = runTest {
        val res = sut.getAll()
        assertEquals(0, res.size)
    }

    @Test
    fun givenNonEmptyDatabaseWhenGetAllIsInvokedThenTermsAreRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        sut.create(model = model)

        val res = sut.getAll()
        assertEquals(1, res.size)
    }

    @Test
    fun givenExistingTermWhenGetByIdIsInvokedThenTermIsRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)

        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals(id, res.id)
    }

    @Test
    fun givenNonExistingTermWhenGetByIdIsInvokedThenNoTermIsRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        sut.create(model = model)

        val res = sut.getById(2)
        assertNull(res)
    }

    @Test
    fun givenExistingTermWhenGetByLemmaIsInvokedThenTermIsRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)

        val res = sut.getBy(lemma = "test", lang = "en")
        assertNotNull(res)
        assertEquals(id, res.id)
    }

    @Test
    fun givenNonExistingTermWhenGetByLemmaIsInvokedThenNoTermIsRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        sut.create(model = model)

        val res = sut.getBy(lemma = "other", lang = "en")
        assertNull(res)
    }

    @Test
    fun givenExistingTermWhenGetByLemmaIsInvokedWithOtherLanguageThenNoTermIsRetrieved() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)

        val res = sut.getBy(lemma = "test", lang = "it")
        assertNull(res)
    }

    @Test
    fun givenExistingTermWhenUpdateIsInvokedThenTermIsUpdated() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)

        sut.update(model.copy(id = id, lemma = "updated"))

        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals("updated", res.lemma)
    }

    @Test
    fun givenExistingTermWhenDeleteIsInvokedThenTermIsDeleted() = runTest {
        val model = GlossaryTermModel(lemma = "test", lang = "en")
        val id = sut.create(model = model)

        sut.delete(model.copy(id = id, lemma = "updated"))

        val res = sut.getById(id)
        assertNull(res)
    }

    @Test
    fun givenExistingTermsWhenDeleteAllIsInvokedThenNoTermsAreRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test 1", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test 2", lang = "en")
        sut.create(model = term1)
        sut.create(model = term2)
        var before = sut.getAll()
        assertEquals(2, before.size)

        sut.deleteAll()

        val res = sut.getAll()
        assertEquals(0, res.size)
    }

    @Test
    fun givenExistingTermsWhenAssociatedThenRelationshipIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)

        val associatedBefore = sut.areAssociated(id1, id2)
        assertFalse(associatedBefore)
        val associatedBeforeReversed = sut.areAssociated(id2, id1)
        assertFalse(associatedBeforeReversed)

        sut.associate(id1, id2)

        val associatedAfter = sut.areAssociated(id1, id2)
        assertTrue(associatedAfter)
        val associatedAfterReversed = sut.areAssociated(id2, id1)
        assertTrue(associatedAfterReversed)
    }

    @Test
    fun givenNoAssociatedTermsWhenGetAssociatedByLangThenNoResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val id1 = sut.create(model = term1)

        val res = sut.getAssociated(term1.copy(id = id1), "it")
        assertEquals(0, res.size)
    }

    @Test
    fun givenExistingAssociatedTermsWhenGetAssociatedByLangThenResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val term3 = GlossaryTermModel(lemma = "test it", lang = "es")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)
        val id3 = sut.create(model = term3)
        sut.associate(id1, id2)
        sut.associate(id1, id3)

        val resIt = sut.getAssociated(term1.copy(id = id1), "it")
        assertEquals(1, resIt.size)

        val resEs = sut.getAssociated(term1.copy(id = id1), "es")
        assertEquals(1, resEs.size)
    }

    @Test
    fun givenExistingAssociatedTermsWhenGetAllAssociatedThenResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val term3 = GlossaryTermModel(lemma = "test it", lang = "es")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)
        val id3 = sut.create(model = term3)
        sut.associate(id1, id2)
        sut.associate(id1, id3)

        val res = sut.getAllAssociated(term1.copy(id = id1))
        assertEquals(2, res.size)
    }

    @Test
    fun givenExistingAssociatedTermsWhenDisassociatedThenNoResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)
        sut.associate(id1, id2)

        val resBefore = sut.getAssociated(term1.copy(id = id1), "it")
        assertEquals(1, resBefore.size)

        sut.disassociate(id1, id2)
        val resAfter = sut.getAssociated(term1.copy(id = id1), "it")
        assertEquals(0, resAfter.size)
    }

    @Test
    fun givenNoAssociatedTermsWhenIsStillReferencedThenCorrectResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val id1 = sut.create(model = term1)

        val res = sut.isStillReferenced(id1)
        assertFalse(res)
    }

    @Test
    fun givenAssociatedTermsWhenIsStillReferencedThenCorrectResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)
        sut.associate(id1, id2)

        val res1 = sut.isStillReferenced(id1)
        assertTrue(res1)
        val res2 = sut.isStillReferenced(id2)
        assertTrue(res2)
    }

    @Test
    fun givenAssociationDeletedWhenIsStillReferencedThenCorrectResultIsRetrieved() = runTest {
        val term1 = GlossaryTermModel(lemma = "test en", lang = "en")
        val term2 = GlossaryTermModel(lemma = "test it", lang = "it")
        val id1 = sut.create(model = term1)
        val id2 = sut.create(model = term2)
        sut.associate(id1, id2)

        sut.disassociate(id1, id2)

        val res1 = sut.isStillReferenced(id1)
        assertFalse(res1)
        val res2 = sut.isStillReferenced(id2)
        assertFalse(res2)
    }
}
