package com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesV2UseCase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultExportDialogComponentTest {
    companion object {
        private val setup by lazy {
            startKoin {
                modules(
                    localizationModule,
                )
            }
        }
    }

    private val lifeCycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockExportResources = mockk<ExportResourcesV2UseCase>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val sut = DefaultExportDialogComponent(
        componentContext = DefaultComponentContext(lifecycle = lifeCycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        completeLanguage = mockCompleteLanguage,
        exportResources = mockExportResources,
        segmentRepository = mockSegmentRepository,
    )

    init {
        setup
    }

    @Test
    fun givenComponentCreatedWhenInitializedThenStateIsDefault() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        val uiState = sut.uiState.value
        assertEquals(ResourceFileType.ANDROID_XML, uiState.selectedResourceType)
        assertEquals(7, uiState.availableResourceTypes.size)
        assertEquals(2, uiState.availableLanguages.size)
        assertEquals(2, uiState.selectedLanguages.size)
        assertEquals("", uiState.outputPath)
        assertEquals("", uiState.languagesError)
        assertEquals("", uiState.resourceTypeError)
        assertEquals("", uiState.outputPathError)
    }

    @Test
    fun givenComponentCreatedWhenChangeResourceFileTypeThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.reduce(ExportDialogComponent.Intent.SelectType(ResourceFileType.IOS_STRINGS))

        val uiState = sut.uiState.value
        assertEquals(ResourceFileType.IOS_STRINGS, uiState.selectedResourceType)
    }

    @Test
    fun givenComponentCreatedWhenRemoveLanguageThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.reduce(ExportDialogComponent.Intent.RemoveLanguage(LanguageModel(code = "it", name = "it")))

        val uiState = sut.uiState.value
        assertEquals(1, uiState.selectedLanguages.size)
        assertEquals("en", uiState.selectedLanguages.first().code)
    }

    @Test
    fun givenComponentCreatedWhenAddLanguageThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()
        sut.reduce(ExportDialogComponent.Intent.RemoveLanguage(LanguageModel(code = "it", name = "it")))
        val stateBefore = sut.uiState.value
        assertEquals(1, stateBefore.selectedLanguages.size)

        sut.reduce(ExportDialogComponent.Intent.AddLanguage(LanguageModel(code = "it", name = "it")))
        val stateAfter = sut.uiState.value
        assertEquals(2, stateAfter.selectedLanguages.size)
    }

    @Test
    fun givenComponentCreatedWhenSetOutputPathThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.reduce(ExportDialogComponent.Intent.SetOutputPath("path"))
        val uiState = sut.uiState.value
        assertEquals("path", uiState.outputPath)
    }

    @Test
    fun givenComponentCreatedWhenOpenFileDialogThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.dialog.configAsFlow<ExportDialogComponent.DialogConfig>().test {
            sut.reduce(ExportDialogComponent.Intent.OpenFileDialog)
            val item = awaitItem()
            assertIs<ExportDialogComponent.DialogConfig.SelectOutputFile>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenCloseDialogThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.dialog.configAsFlow<ExportDialogComponent.DialogConfig>().test {
            sut.reduce(ExportDialogComponent.Intent.CloseDialog)
            val item = awaitItem()
            assertIs<ExportDialogComponent.DialogConfig.None>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyOutputPathThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.reduce(ExportDialogComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.outputPathError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyLanguagesThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()
        sut.reduce(ExportDialogComponent.Intent.RemoveLanguage(LanguageModel(code = "en", name = "en")))
        sut.reduce(ExportDialogComponent.Intent.RemoveLanguage(LanguageModel(code = "it", name = "it")))

        sut.reduce(ExportDialogComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("message_select_one_language".localized(), uiState.languagesError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithValidData() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        coEvery { mockLanguageRepository.getByCode(any(), any()) } returns LanguageModel()
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(
            SegmentModel(key = "key"),
        )
        coEvery { mockExportResources.invoke(any(), any(), any()) } returns Unit
        lifeCycle.create()
        sut.reduce(ExportDialogComponent.Intent.SetOutputPath("path"))

        sut.effects.test {
            sut.reduce(ExportDialogComponent.Intent.Submit)
            val item = awaitItem()
            assertIs<ExportDialogComponent.Effect.Done>(item)
        }

        coVerify {
            mockExportResources.invoke(
                data = withArg { assertEquals(2, it.size) },
                path = "path",
                type = ResourceFileType.ANDROID_XML,
            )
        }
    }
}
