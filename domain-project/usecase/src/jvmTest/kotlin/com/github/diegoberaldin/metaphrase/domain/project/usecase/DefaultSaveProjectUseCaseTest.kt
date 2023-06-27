package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import java.io.File
import java.io.FileReader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultSaveProjectUseCaseTest {

    companion object {
        val setup by lazy {
            startKoin { modules(localizationModule) }
            L10n.setLanguage("en")
        }
    }

    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val sut = DefaultSaveProjectUseCase(
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
    )

    init {
        setup
    }

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
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(id = 0, code = "en", isBase = true)
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(id = 0, code = "en", isBase = true),
            LanguageModel(id = 1, code = "it"),
        )
        coEvery { mockSegmentRepository.getAll(0) } returns listOf(
            SegmentModel(key = "app_intro", text = "Please open a project by selecting one of the following items."),
        )
        coEvery { mockSegmentRepository.getAll(1) } returns listOf(
            SegmentModel(key = "app_intro", text = "Apri un progetto selezionando una delle seguenti voci."),
        )
        val idSlot = slot<Int>()
        coEvery { mockSegmentRepository.getByKey(any(), capture(idSlot)) } answers {
            when (idSlot.captured) {
                1 -> SegmentModel(
                    key = "app_intro",
                    text = "Please open a project by selecting one of the following items.",
                )

                else -> SegmentModel(key = "app_intro", text = "Apri un progetto selezionando una delle seguenti voci.")
            }
        }
        val path = MockFileManager.getFilePath()
        sut.invoke(
            projectId = 1,
            path = path,
        )

        FileReader(File(path)).use { reader ->
            val content = reader.readText()
            assertEquals(
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
					Please open a project by selecting one of the following items.
				</seg>
			</tuv>
		</tu>
	</body>
</tmx>
                """.trimIndent(),
                content,
            )
        }
    }
}
