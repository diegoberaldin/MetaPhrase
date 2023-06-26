package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.domain.formats.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.formats.MockFileManager
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultParseJsonUseCaseTest {

    private val sut = DefaultParseJsonUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("strings", ".json")
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
{
    "app_intro": "Please open a project by selecting one of the following items."
}
                """.trimIndent(),
            )
        }
        val res = sut.invoke(path)
        assertEquals(1, res.size)
        val message = res.first()
        assertEquals("app_intro", message.key)
        assertEquals("Please open a project by selecting one of the following items.", message.text)
    }
}
