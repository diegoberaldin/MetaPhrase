package com.github.diegoberaldin.metaphrase.domain.formats.ios

import com.github.diegoberaldin.metaphrase.domain.formats.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.formats.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.fail
import java.io.File
import java.io.FileReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultExportIosResourceUseCaseTest {

    private val sut = DefaultExportIosResourcesUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("en-US", ".strings")
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
        sut.invoke(segments, path)
        try {
            FileReader(File(path)).use { reader ->
                val content = reader.readText()
                assertEquals(
                    """
// Localizable.strings

"app_intro" = "Please open a project by selecting one of the following items.";

                    """.trimIndent(),
                    content,
                )
            }
        } catch (e: Throwable) {
            fail(e)
        }
    }
}
