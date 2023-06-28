package com.github.diegoberaldin.metaphrase.domain.formats.resx

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultParseResxUseCaseTest {

    private val sut = DefaultParseResxUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("strings", ".resx")
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
<root>
	<data name="app_intro" xml:space="preserve">
		<value>Please open a project by selecting one of the following items.</value>
	</data>
</root>
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
