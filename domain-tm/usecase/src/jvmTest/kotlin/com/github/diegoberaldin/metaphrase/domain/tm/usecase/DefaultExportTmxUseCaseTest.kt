package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import java.io.File
import java.io.FileReader
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultExportTmxUseCaseTest {
    companion object {
        val setup by lazy {
            startKoin { modules(localizationModule) }
            L10n.setLanguage("en")
        }
    }

    init {
        setup
    }

    private val mockRepository = mockk<MemoryEntryRepository>()
    private val sut = DefaultExportTmxUseCase(
        memoryEntryRepository = mockRepository,
        dispatchers = MockCoroutineDispatcherProvider,
    )

    @BeforeTest
    fun setup() {
        MockFileManager.setup("memory", ".tmx")
    }

    @Test
    fun givenUseCaseWhenInvokedThenMemoryIsCleared() = runTest {
        coEvery { mockRepository.getLanguageCodes() } returns listOf("en", "it")
        val entry = TranslationMemoryEntryModel(
            identifier = "app_intro",
            sourceText = "Please open a project by selecting one of the following items.",
            sourceLang = "en",
            targetText = "Apri un progetto selezionando una delle seguenti voci.",
            targetLang = "it",
        )
        coEvery { mockRepository.getEntries("en") } returns listOf(
            entry,
        )
        coEvery { mockRepository.getTranslation("it", "app_intro") } returns entry
        val path = MockFileManager.getFilePath()

        sut.invoke("en", path)

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
					Apri un progetto selezionando una delle seguenti voci.
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
