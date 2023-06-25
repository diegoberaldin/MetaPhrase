package com.github.diegoberaldin.metaphrase.core.common.files

import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultFileManagerTest {

    private val sut = DefaultFileManager
    private var testFile: File? = null

    @AfterTest
    fun teardown() {
        testFile?.also {
            it.delete()
        }
    }

    @Test
    fun whenInvokedWithFileNameThenFileCanBeRead() {
        val path = sut.getFilePath("test.txt")
        testFile = File(path)
        testFile?.also {
            assertFalse(it.exists())
            assertTrue(it.createNewFile())
            assertTrue(it.exists())
            assertTrue(it.canRead())
        }
    }

    @Test
    fun whenInvokedWithFileNameThenFileCanBeWritten() {
        val path = sut.getFilePath("test.txt")
        testFile = File(path)
        testFile?.also {
            assertFalse(it.exists())
            assertTrue(it.createNewFile())
            assertTrue(it.exists())
            assertTrue(it.canWrite())
        }
    }
}
