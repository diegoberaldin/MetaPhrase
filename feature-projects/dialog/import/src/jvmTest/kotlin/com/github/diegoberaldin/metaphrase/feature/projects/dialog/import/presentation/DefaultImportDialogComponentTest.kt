package com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesV2UseCase
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
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
import kotlin.test.assertTrue

class DefaultImportDialogComponentTest {
    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    localizationModule,
                )
            }
        }
    }

    private val lifeCycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val mockImportResources = mockk<ImportResourcesV2UseCase>()
    private val sut = DefaultImportDialogComponent(
        componentContext = DefaultComponentContext(lifecycle = lifeCycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        completeLanguage = mockCompleteLanguage,
        importResources = mockImportResources,
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
        assertEquals(2, uiState.languages.size)
        assertTrue(uiState.languages.values.all { it.isEmpty() })
        assertEquals("", uiState.languagesError)
        assertEquals("", uiState.resourceTypeError)
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

        sut.reduce(ImportDialogComponent.Intent.SelectType(ResourceFileType.IOS_STRINGS))

        val uiState = sut.uiState.value
        assertEquals(ResourceFileType.IOS_STRINGS, uiState.selectedResourceType)
    }

    @Test
    fun givenComponentCreatedWhen() = runTest {
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(
            LanguageModel(code = "en", name = "en"),
            LanguageModel(code = "it", name = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers { languageSlot.captured }
        lifeCycle.create()

        sut.reduce(ImportDialogComponent.Intent.SetInputPath(path = "path", lang = "en"))

        val uiState = sut.uiState.value
        assertEquals(ResourceFileType.ANDROID_XML, uiState.selectedResourceType)
        assertEquals(7, uiState.availableResourceTypes.size)
        assertEquals(2, uiState.languages.size)
        assertEquals(uiState.languages[LanguageModel(code = "en", name = "en")], "path")
        assertEquals("", uiState.languagesError)
        assertEquals("", uiState.resourceTypeError)
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

        sut.dialog.configAsFlow<ImportDialogComponent.DialogConfig>().test {
            sut.reduce(ImportDialogComponent.Intent.OpenFileDialog("en"))
            val item = awaitItem()
            assertIs<ImportDialogComponent.DialogConfig.OpenFile>(item)
            assertEquals("en", item.lang)
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

        sut.dialog.configAsFlow<ImportDialogComponent.DialogConfig>().test {
            sut.reduce(ImportDialogComponent.Intent.CloseDialog)
            val item = awaitItem()
            assertIs<ImportDialogComponent.DialogConfig.None>(item)
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

        sut.reduce(ImportDialogComponent.Intent.Submit)

        val uiState = sut.uiState.value
        assertEquals("message_select_one_file".localized(), uiState.languagesError)
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
        coEvery { mockImportResources.invoke(any(), any(), any()) } returns Unit
        lifeCycle.create()
        sut.reduce(ImportDialogComponent.Intent.SetInputPath("path_en", "en"))
        sut.reduce(ImportDialogComponent.Intent.SetInputPath("path_it", "it"))

        sut.effects.test {
            sut.reduce(ImportDialogComponent.Intent.Submit)
            val item = awaitItem()
            assertIs<ImportDialogComponent.Effect.Done>(item)
        }

        coVerify {
            mockImportResources.invoke(
                type = ResourceFileType.ANDROID_XML,
                projectId = 0,
                paths = mapOf(
                    "en" to "path_en",
                    "it" to "path_it",
                ),
            )
        }
    }
}
