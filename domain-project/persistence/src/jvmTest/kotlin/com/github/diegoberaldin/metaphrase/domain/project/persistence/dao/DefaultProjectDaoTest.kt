package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.persistence.DefaultAppDatabase
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultProjectDaoTest {

    private lateinit var sut: DefaultProjectDao

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
        sut = appDb.projectDao()
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenEmptyDatabaseWhenGetAllInvokedThenNoProjectsAreReturned() = runTest {
        val res = sut.getAll()
        assertEquals(0, res.size)
    }

    @Test
    fun givenDaoWhenCreateThenEntityIsInserted() = runTest {
        val model = ProjectModel(name = "test")
        val id = sut.create(model)
        assertTrue(id > 0)
    }

    @Test
    fun givenNonEmptyDatabaseWhenGetAllInvokedThenProjectsAreReturned() = runTest {
        val resBefore = sut.getAll()
        assertEquals(0, resBefore.size)

        val model = ProjectModel(name = "test")
        sut.create(model)

        val resAfter = sut.getAll()
        assertEquals(1, resAfter.size)
    }

    @Test
    fun givenNonEmptyDatabaseWhenGetByIdInvokedThenProjectsAreReturned() = runTest {
        val model = ProjectModel(name = "test")
        val id = sut.create(model)

        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals("test", res.name)
    }

    @Test
    fun givenExistingProjectWhenUpdatedInvokedThenEntityIsUpdated() = runTest {
        val model = ProjectModel(name = "test")
        val id = sut.create(model)

        sut.update(model.copy(id = id, name = "updated"))
        val res = sut.getById(id)
        assertNotNull(res)
        assertEquals("updated", res.name)
    }

    @Test
    fun givenExistingProjectWhenDeleteInvokedThenEntityIsDeleted() = runTest {
        val model = ProjectModel(name = "test")
        val id = sut.create(model)
        val resBefore = sut.getById(id)
        assertNotNull(resBefore)

        sut.delete(model.copy(id = id))
        val resAfter = sut.getById(id)
        assertNull(resAfter)
    }

    @Test
    fun givenExistingProjectWhenDeleteAllInvokedThenEntityIsDeleted() = runTest {
        val model = ProjectModel(name = "test")
        sut.create(model)
        val resBefore = sut.getAll()
        assertEquals(1, resBefore.size)

        sut.deleteAll()
        val resAfter = sut.getAll()
        assertEquals(0, resAfter.size)
    }
}
