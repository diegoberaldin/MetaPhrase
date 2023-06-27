package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.SegmentDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultSegmentRepositoryTest {
    private val dao = mockk<SegmentDao>()
    private val sut = DefaultSegmentRepository(
        dao = dao,
    )

    @Test
    fun givenRepositoryWhenCreateThenItemIsCreated() = runTest {
        coEvery { dao.create(any(), any()) } returns 1
        val model = SegmentModel(key = "key")
        val id = sut.create(model = model, languageId = 1)
        assertEquals(1, id)
        coVerify { dao.create(withArg { assertEquals("key", it.key) }, 1) }
    }

    @Test
    fun givenRepositoryWhenCreateBatchThenItemIsCreated() = runTest {
        coEvery { dao.createBatch(any(), any()) } returns Unit
        val model1 = SegmentModel(key = "key1")
        val model2 = SegmentModel(key = "key2")
        sut.createBatch(models = listOf(model1, model2), languageId = 1)
        coVerify {
            dao.createBatch(
                withArg {
                    assertEquals(2, it.size)
                    assertEquals("key1", it[0].key)
                    assertEquals("key2", it[1].key)
                },
                1,
            )
        }
    }

    @Test
    fun givenRepositoryWhenGetByIdThenDataIsRetrieved() = runTest {
        val idSlot = slot<Int>()
        coEvery { dao.getById(capture(idSlot)) } answers { SegmentModel(id = idSlot.captured) }
        val res = sut.getById(1)
        assertNotNull(res)
        assertEquals(1, res.id)
        coVerify { dao.getById(1) }
    }

    @Test
    fun givenRepositoryWhenGetByKeyThenDataIsRetrieved() = runTest {
        val keySlot = slot<String>()
        coEvery { dao.getByKey(capture(keySlot), any()) } answers { SegmentModel(key = keySlot.captured) }
        val res = sut.getByKey("key", 1)
        assertNotNull(res)
        assertEquals("key", res.key)
        coVerify { dao.getByKey("key", 1) }
    }

    @Test
    fun givenEmptyRepositoryWhenGetAllThenDataIsRetrieved() = runTest {
        coEvery { dao.getAll(any()) } returns emptyList()
        val res = sut.getAll(1)
        assertEquals(0, res.size)
        coVerify { dao.getAll(1) }
    }

    @Test
    fun givenRepositoryWhenGetAllThenDataIsRetrieved() = runTest {
        coEvery { dao.getAll(any()) } returns listOf(SegmentModel())
        val res = sut.getAll(1)
        assertEquals(1, res.size)
        coVerify { dao.getAll(1) }
    }

    @Test
    fun givenRepositoryWhenUpdateThenItemIsUpdated() = runTest {
        coEvery { dao.update(any()) } returns Unit
        val model = SegmentModel(id = 1)
        sut.update(model = model)
        coVerify { dao.update(withArg { assertEquals(1, it.id) }) }
    }

    @Test
    fun givenRepositoryWhenDeleteThenItemIsDeleted() = runTest {
        coEvery { dao.delete(any()) } returns Unit
        val model = SegmentModel(id = 1)
        sut.delete(model = model)
        coVerify { dao.delete(withArg { assertEquals(1, it.id) }) }
    }

    @Test
    fun givenRepositoryWhenGetUntranslatableThenDataIsRetrieved() = runTest {
        coEvery { dao.getUntranslatable(any()) } returns listOf(SegmentModel())
        val res = sut.getUntranslatable(1)
        assertEquals(1, res.size)
        coVerify { dao.getUntranslatable(1) }
    }

    @Test
    fun givenRepositoryWhenSearchThenDataIsRetrieved() = runTest {
        coEvery { dao.search(any(), any(), any(), any(), any(), any()) } returns listOf(SegmentModel())
        val res = sut.search(1)
        assertEquals(1, res.size)
        coVerify { dao.search(any(), any(), any(), any(), any(), any()) }
    }
}
