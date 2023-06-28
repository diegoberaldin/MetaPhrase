package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.persistence.AppDatabase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultSegmentDaoTest {
    private lateinit var appDb: AppDatabase
    private lateinit var sut: DefaultSegmentDao
    private var projectId: Int = 0
    private var languageId: Int = 0

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
        sut = appDb.segmentDao()

        val projectDao = appDb.projectDao()
        val languageDao = appDb.languageDao()
        runBlocking {
            projectId = projectDao.create(ProjectModel(name = "test"))
            languageId = languageDao.create(LanguageModel(code = "en"), projectId)
        }
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyDatabaseWhenGetAllThenEmptyListIsRetrieved() = runTest {
        val res = sut.getAll(languageId)
        assertEquals(0, res.size)
    }

    @Test
    fun givenDatabaseWhenCreateThenEntityIsInserted() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        val id = sut.create(model, languageId)
        assertTrue(id > 0)
    }

    @Test
    fun givenDatabaseWhenGetAllThenListIsRetrieved() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        sut.create(model, languageId)

        val res = sut.getAll(languageId)
        assertEquals(1, res.size)
    }

    @Test
    fun givenDatabaseWhenCreateBatchThenEntitiesAreInserted() = runTest {
        sut.createBatch(
            models = listOf(
                SegmentModel(key = "key1", text = "message 1"),
                SegmentModel(key = "key2", text = "message 2"),
            ),
            languageId = languageId,
        )
        val res = sut.getAll(languageId)
        assertEquals(2, res.size)
    }

    @Test
    fun givenDatabaseWhenUpdateThenEntityIsUpdated() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        val id = sut.create(model, languageId)
        val resBefore = sut.getById(id)
        assertNotNull(resBefore)
        assertEquals("message", resBefore.text)

        sut.update(model.copy(id = id, text = "updated"))

        val resAfter = sut.getById(id)
        assertNotNull(resAfter)
        assertEquals("updated", resAfter.text)
    }

    @Test
    fun givenDatabaseWhenGetByIdThenDataIsRetrieved() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        val id = sut.create(model, languageId)

        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals("key", res.key)
        assertEquals("message", res.text)
    }

    @Test
    fun givenDatabaseWhenGetByKeyThenDataIsRetrieved() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        sut.create(model, languageId)

        val res = sut.getByKey(key = "key", languageId = languageId)
        assertNotNull(res)
        assertEquals("key", res.key)
        assertEquals("message", res.text)
    }

    @Test
    fun givenDatabaseWhenDeleteThenEntityIsDeleted() = runTest {
        val model = SegmentModel(key = "key", text = "message")
        val id = sut.create(model = model, languageId = languageId)
        val resBefore = sut.getById(id)
        assertNotNull(resBefore)

        sut.delete(model.copy(id = id))

        val resAfter = sut.getById(id)
        assertNull(resAfter)
    }

    @Test
    fun givenSomeUntranslatableSegmentsWhenGetUntranslatableThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.getUntranslatable(languageId)

        assertEquals(1, res.size)
    }

    @Test
    fun givenSomeUntranslatableSegmentsWhenSearchTranslatableThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.search(
            languageId = languageId,
            filter = TranslationUnitTypeFilter.TRANSLATABLE,
        )

        assertEquals(1, res.size)
    }

    @Test
    fun givenSomeSegmentsWhenSearchAllThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.search(
            languageId = languageId,
            filter = TranslationUnitTypeFilter.ALL,
        )

        assertEquals(2, res.size)
    }

    @Test
    fun givenSomeSegmentsWhenSearchWithQueryForMessageThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.search(
            languageId = languageId,
            search = " 1",
        )

        assertEquals(1, res.size)
    }

    @Test
    fun givenSomeSegmentsWhenSearchWithQueryForKeyThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.search(
            languageId = languageId,
            search = "key1",
        )

        assertEquals(1, res.size)
    }

    @Test
    fun givenSomeSegmentsWhenSearchPaginationForKeyThenDataIsRetrieved() = runTest {
        val model1 = SegmentModel(key = "key1", text = "message 1")
        val model2 = SegmentModel(key = "key2", text = "message 2", translatable = false)
        sut.createBatch(models = listOf(model1, model2), languageId = languageId)

        val res = sut.search(
            languageId = languageId,
            limit = 1,
        )

        assertEquals(1, res.size)
    }
}
