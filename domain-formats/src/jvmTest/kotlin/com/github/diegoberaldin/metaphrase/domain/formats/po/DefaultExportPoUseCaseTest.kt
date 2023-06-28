package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultExportPoUseCaseTest {

    private val sut = DefaultExportPoUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("messages", ".po")
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenUseCaseWhenInvokedWithSegmentsThenMessagesAreWritten() = runTest {
        val path = MockFileManager.getFilePath()
        val segments = listOf(
            SegmentModel(
                key = "app_intro",
                text = "Please open a project by selecting one of the following items.",
            ),
        )
        sut.invoke(segments, path, "en")
        try {
            FileReader(File(path)).use { reader ->
                val content = reader.readText()
                assertEquals(
                    """
msgid ""
msgstr ""
"Language: en\n"
"MIME-Version: 1.0\n"
"Content-Type: text/plain; charset=UTF-8\n"
"Content-Transfer-Encoding: 8bit\n"

msgid app_intro
msgstr Please open a project by selecting one of the following items.


                    """.trimIndent(),
                    content,
                )
            }
        } catch (e: Throwable) {
            fail(e)
        }
    }
}
