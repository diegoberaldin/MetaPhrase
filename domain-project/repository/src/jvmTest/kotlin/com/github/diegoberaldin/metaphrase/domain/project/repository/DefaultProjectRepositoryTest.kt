package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.ProjectDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultProjectRepositoryTest {
    private val dao = mockk<ProjectDao>()
    private val sut = DefaultProjectRepository(
        dao = dao,
    )

    @Test
    fun givenRepositoryWhenCreateThenDataIsCreated() = runTest {
        coEvery { dao.create(any()) } returns 0

        val model = ProjectModel(name = "test")
        val id = sut.create(model = model)

        assertEquals(0, id)
        coVerify { dao.create(withArg { assertEquals("test", it.name) }) }
    }

    @Test
    fun givenRepositoryWhenSetNeedsSavingThenValueIsUpdated() {
        val needsSaving = sut.isNeedsSaving()

        sut.setNeedsSaving(!needsSaving)

        val res = sut.isNeedsSaving()
        assertEquals(!needsSaving, res)
    }

    @Test
    fun givenRepositoryWhenObserveNeedsSavingThenValueAreEmitted() = runTest {
        val needsSaving = sut.observeNeedsSaving()

        sut.setNeedsSaving(true)
        assertTrue(needsSaving.first())
    }

    @Test
    fun givenEmptyRepositoryWhenGetAllThenEmptyListIsReturned() = runTest {
        coEvery { dao.getAll() } returns emptyList()

        val res = sut.getAll()

        assertEquals(0, res.size)
        coVerify { dao.getAll() }
    }

    @Test
    fun givenRepositoryWhenGetAllThenListIsReturned() = runTest {
        coEvery { dao.getAll() } returns listOf(ProjectModel())

        val res = sut.getAll()

        assertEquals(1, res.size)
        coVerify { dao.getAll() }
    }

    @Test
    fun givenRepositoryWhenGetByIdThenDataIsRetrieved() = runTest {
        val idSlot = slot<Int>()
        coEvery { dao.getById(capture(idSlot)) } answers { ProjectModel(id = idSlot.captured) }

        val res = sut.getById(1)

        assertNotNull(res)
        coVerify { dao.getById(1) }
    }

    @Test
    fun givenRepositoryWhenObserveByIdThenDataIsRetrieved() = runTest {
        val idSlot = slot<Int>()
        coEvery { dao.getById(capture(idSlot)) } answers { ProjectModel(id = idSlot.captured) }

        val res = sut.observeById(1)

        val project = res.first()
        assertNotNull(project)
        coVerify(exactly = 1) { dao.getById(1) }
    }

    @Test
    fun givenRepositoryWhenUpdateThenDataIsUpdated() = runTest {
        coEvery { dao.update(any()) } returns Unit

        val model = ProjectModel(name = "test", id = 0)
        sut.update(model = model)

        coVerify { dao.update(withArg { assertEquals(0, it.id) }) }
    }

    @Test
    fun givenRepositoryWhenDeleteThenDataIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit

        val model = ProjectModel(name = "test", id = 0)
        sut.delete(model = model)

        coVerify { dao.delete(withArg { assertEquals(0, it.id) }) }
    }

    @Test
    fun givenRepositoryWhenDeleteAllThenDataIsDeleted() = runTest {
        coEvery { dao.deleteAll() } returns Unit

        sut.deleteAll()

        coVerify { dao.deleteAll() }
    }
}
