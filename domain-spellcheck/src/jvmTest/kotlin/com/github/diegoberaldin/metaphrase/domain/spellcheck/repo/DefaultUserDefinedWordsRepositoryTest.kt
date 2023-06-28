package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultUserDefinedWordsRepositoryTest {
    private val mockFileManager = mockk<FileManager>()
    private val sut = DefaultUserDefinedWordsRepository(
        fileManager = mockFileManager,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("spelling", ".txt")
        val path = MockFileManager.getFilePath()
        val dir = File(File(path).parent)
        every { mockFileManager.getFilePath(any()) } returns dir.path
    }

    @AfterTest
    fun teardown() {
        val path = mockFileManager.getFilePath("spelling")
        File(path).delete()
        MockFileManager.teardown()
    }

    @Test
    fun givenRepositoryEmptyWhenQueriedThenEmptyListIsReturned() = runTest {
        val res = sut.getAll("en")
        assertEquals(0, res.size)
    }

    @Test
    fun givenRepositoryEmptyWhenWordAddedThenListIsReturned() = runTest {
        val resBefore = sut.getAll("en")
        assertEquals(0, resBefore.size)

        sut.add("MetaPhrase", "en")

        val resAfter = sut.getAll("en")
        assertEquals(1, resAfter.size)
        val word = resAfter.first()
        assertEquals("MetaPhrase", word)
    }

    @Test
    fun givenRepositoryNotEmptyWhenClearedThenListIsCleared() = runTest {
        sut.add("MetaPhrase", "en")
        val resBefore = sut.getAll("en")
        assertEquals(1, resBefore.size)

        sut.clear("en")

        val resAfter = sut.getAll("en")
        assertEquals(0, resAfter.size)
    }
}
