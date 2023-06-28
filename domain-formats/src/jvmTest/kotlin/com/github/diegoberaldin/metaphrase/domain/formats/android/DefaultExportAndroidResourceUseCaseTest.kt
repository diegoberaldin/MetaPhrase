package com.github.diegoberaldin.metaphrase.domain.formats.android

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

class DefaultExportAndroidResourceUseCaseTest {

    private val sut = DefaultExportAndroidResourcesUseCase(
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
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="app_intro">Please open a project by selecting one of the following items.</string>
</resources>
                    """.trimIndent(),
                    content,
                )
            }
        } catch (e: Throwable) {
            fail(e)
        }
    }

    @Test
    fun givenUseCaseWhenInvokedWithSegmentsThenMessagesAreWrittenWithUntranslatable() = runTest {
        val path = MockFileManager.getFilePath()
        val segments = listOf(
            SegmentModel(
                key = "app_name",
                text = "MetaPhrase",
                translatable = false,
            ),
        )
        sut.invoke(segments, path)
        try {
            FileReader(File(path)).use { reader ->
                val content = reader.readText()
                assertEquals(
                    """
<?xml version="1.0" encoding="UTF-8"?>
<resources>
	<string name="app_name" translatable="false">MetaPhrase</string>
</resources>
                    """.trimIndent(),
                    content,
                )
            }
        } catch (e: Throwable) {
            fail(e)
        }
    }
}
