package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.RecentProjectDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultRecentProjectRepositoryTest {
    private val dao = mockk<RecentProjectDao>()
    private val sut = DefaultRecentProjectRepository(
        dao = dao,
    )

    @Test
    fun givenRepositoryWhenCreateThenItemIsCreated() = runTest {
        coEvery { dao.create(any()) } returns 1
        val model = RecentProjectModel(name = "test")
        val res = sut.create(model)
        assertEquals(1, res)
        coVerify { dao.create(withArg { assertEquals("test", it.name) }) }
    }

    @Test
    fun givenRepositoryWhenGetAllThenListOfItemsIsRetrieved() = runTest {
        coEvery { dao.getAll() } returns listOf(RecentProjectModel(id = 1))
        val res = sut.getAll()
        assertEquals(1, res.size)
        assertEquals(1, res.first().id)
        coVerify { dao.getAll() }
    }

    @Test
    fun givenRepositoryWhenGetByNameThenListOfItemsIsRetrieved() = runTest {
        val nameSlot = slot<String>()
        coEvery { dao.getByName(capture(nameSlot)) } answers { RecentProjectModel(name = nameSlot.captured) }
        val res = sut.getByName("test")
        assertNotNull(res)
        assertEquals("test", res.name)
        coVerify { dao.getByName("test") }
    }

    @Test
    fun givenRepositoryWhenDeleteThenItemIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit
        sut.delete(RecentProjectModel(id = 1))
        coVerify { dao.delete(withArg { assertEquals(1, it.id) }) }
    }

    @Test
    fun givenRepositoryWhenObserveAllThenListOfItemsIsRetrieved() = runTest {
        coEvery { dao.getAll() } returns listOf(RecentProjectModel(id = 1))
        val res = sut.observeAll().first()
        assertEquals(1, res.size)
        assertEquals(1, res.first().id)
        coVerify { dao.getAll() }
    }
}
