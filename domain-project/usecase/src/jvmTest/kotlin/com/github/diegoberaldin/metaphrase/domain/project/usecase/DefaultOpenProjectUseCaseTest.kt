package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.io.File
import java.io.FileWriter
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultOpenProjectUseCaseTest {
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultOpenProjectUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
        projectRepository = mockProjectRepository,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("test", ".tmx")
    }

    @AfterTest
    fun teardown() {
        MockFileManager.teardown()
    }

    @Test
    fun givenUseCaseWhenInvokedThenProjectDataAreCreated() = runTest {
        coEvery { mockProjectRepository.create(any()) } returns 1
        coEvery { mockLanguageRepository.getByCode(any(), any()) } returns null
        coEvery { mockLanguageRepository.create(any(), any()) } returns 1
        coEvery { mockSegmentRepository.createBatch(any(), any()) } returns Unit
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
        sut.invoke(path)
        coVerify {
            mockProjectRepository.create(any())
            mockLanguageRepository.create(withArg { assertEquals("en", it.code) }, any())
            mockLanguageRepository.create(withArg { assertEquals("it", it.code) }, any())
        }
        coVerify(exactly = 2) {
            mockSegmentRepository.createBatch(
                withArg {
                    assertEquals("app_intro", it.first().key)
                },
                any(),
            )
        }
    }
}
