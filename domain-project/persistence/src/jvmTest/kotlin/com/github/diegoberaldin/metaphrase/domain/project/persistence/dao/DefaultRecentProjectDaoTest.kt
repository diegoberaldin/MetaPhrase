package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.persistence.DefaultAppDatabase
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultRecentProjectDaoTest {

    private lateinit var sut: DefaultRecentProjectDao

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
        sut = appDb.recentProjectDao()
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyDatabaseWhenGetAllThenEmptyListIsReturned() = runTest {
        val res = sut.getAll()
        assertEquals(0, res.size)
    }

    @Test
    fun givenDatabaseWhenCreateThenEntityIsInserted() = runTest {
        val model = RecentProjectModel(name = "test")
        val id = sut.create(model)
        assertTrue(id > 0)
    }

    @Test
    fun givenDatabaseNotEmptyWhenGetAllThenEntitiesAreRetrieved() = runTest {
        val model = RecentProjectModel(name = "test")
        sut.create(model)

        val res = sut.getAll()
        assertEquals(1, res.size)
    }

    @Test
    fun givenDatabaseWhenGetByNameThenEntityIsRetrieved() = runTest {
        val model = RecentProjectModel(name = "test")
        sut.create(model)

        val res = sut.getByName("test")
        assertNotNull(res)
        assertEquals("test", res.name)
    }

    @Test
    fun givenDatabaseWhenDeleteThenEntityIsUpdated() = runTest {
        val model = RecentProjectModel(name = "test")
        val id = sut.create(model)
        val resBefore = sut.getAll()
        assertEquals(1, resBefore.size)

        sut.delete(model.copy(id = id))

        val resAfter = sut.getAll()
        assertEquals(0, resAfter.size)
    }
}
