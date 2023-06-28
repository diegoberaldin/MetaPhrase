package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class DefaultImportTmxUseCaseTest {
    private val mockRepository = mockk<MemoryEntryRepository>()
    private val sut = DefaultImportTmxUseCase(
        memoryEntryRepository = mockRepository,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("memory", ".tmx")
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenUseCaseWhenInvokedThenUnitsAreImported() = runTest {
        val path = MockFileManager.getFilePath()
        FileWriter(File(path)).use { writer ->
            writer.write(
                """
<?xml version="1.0" encoding="UTF-8"?>
<tmx version="1.4">
	<header creationTool="MetaPhrase" creationToolVersion="1.0.0" segtype="sentence" o-tmf="tmx" adminLang="en-US" srcLang="en" datatype="plaintext"/>
	<body>
		<tu tuid="app_intro">
			<tuv xml:lang="en">
				<seg>
					Please open a project by selecting one of the following items.
				</seg>
			</tuv>
			<tuv xml:lang="it">
				<seg>
					Apri un progetto selezionando una delle seguenti voci.
				</seg>
			</tuv>
		</tu>
	</body>
</tmx>
                """.trimIndent(),
            )
        }
        coEvery { mockRepository.create(any()) } returns 0
        sut.invoke(path)
        coVerify { mockRepository.create(withArg { assertEquals("app_intro", it.identifier) }) }
    }
}
