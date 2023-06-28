package com.github.diegoberaldin.metaphrase.domain.formats.android

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultParseAndroidResourceUseCaseTest {

    private val sut = DefaultParseAndroidResourcesUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("strings", ".xml")
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenUseCaseWhenInvokedThenMessagesAreParsed() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="app_intro">Please open a project by selecting one of the following items.</string>
</resources>
                """.trimIndent(),
            )
        }
        val res = sut.invoke(path)
        assertEquals(1, res.size)
        val message = res.first()
        assertEquals("app_intro", message.key)
        assertEquals("Please open a project by selecting one of the following items.", message.text)
        assertTrue(message.translatable)
    }

    @Test
    fun givenUseCaseWhenInvokedThenMessagesAreParsedWithUntranslatable() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="app_name" translatable="false">MetaPhrase</string>
</resources>
                """.trimIndent(),
            )
        }
        val res = sut.invoke(path)
        assertEquals(1, res.size)
        val message = res.first()
        assertEquals("app_name", message.key)
        assertEquals("MetaPhrase", message.text)
        assertFalse(message.translatable)
    }
}
