package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockFileManager
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.persistence.di.persistenceModule
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.di.projectModule
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ImportSegmentsUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.SaveProjectUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ValidatePlaceholdersUseCase
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.ValidateSpellingUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ExportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.SyncProjectWithTmUseCase
import com.github.diegoberaldin.metaphrase.feature.translate.di.translateModule
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultTranslateComponentTest {

    companion object {
        private val setup by lazy {
            startKoin {
                modules(
                    commonModule,
                    localizationModule,
                    persistenceModule,
                    languageModule,
                    projectModule,
                    translateModule,
                    module {
                        single<TranslateToolbarComponent> {
                            mockk<TranslateToolbarComponent> {
                                every { uiState } returns MutableStateFlow(
                                    TranslateToolbarComponent.UiState(
                                        currentLanguage = LanguageModel(
                                            code = "en",
                                            isBase = true,
                                        ),
                                    ),
                                )
                            }
                        }
                    },
                )
            }
            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockRecentProjectRepository = mockk<RecentProjectRepository>()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockGlossaryTermRepository = mockk<GlossaryTermRepository>()
    private val mockImportResources = mockk<ImportResourcesUseCase>()
    private val mockImportSegments = mockk<ImportSegmentsUseCase>()
    private val mockExportResources = mockk<ExportResourcesUseCase>()
    private val mockValidatePlaceholders = mockk<ValidatePlaceholdersUseCase>()
    private val mockExportToTmx = mockk<ExportTmxUseCase>()
    private val mockValidateSpelling = mockk<ValidateSpellingUseCase>()
    private val mockSyncProjectWithTm = mockk<SyncProjectWithTmUseCase>()
    private val mockSaveProject = mockk<SaveProjectUseCase>()
    private val mockMachineTranslationRepository = mockk<MachineTranslationRepository>()
    private val mockNotificationCenter = mockk<NotificationCenter>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val sut = DefaultTranslateComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        projectRepository = mockProjectRepository,
        recentProjectRepository = mockRecentProjectRepository,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
        glossaryTermRepository = mockGlossaryTermRepository,
        importResources = mockImportResources,
        importSegments = mockImportSegments,
        exportResources = mockExportResources,
        validatePlaceholders = mockValidatePlaceholders,
        exportToTmx = mockExportToTmx,
        validateSpelling = mockValidateSpelling,
        syncProjectWithTm = mockSyncProjectWithTm,
        saveProject = mockSaveProject,
        machineTranslationRepository = mockMachineTranslationRepository,
        notificationCenter = mockNotificationCenter,
        keyStore = mockKeyStore,
    )

    init {
        setup
    }

    @Test
    fun givenComponentResumedWhenProjectEmptyThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        runOnUiThread {
            lifecycle.resume()
        }

        val uiState = sut.uiState.value
        assertEquals(0, uiState.unitCount)
        assertEquals("test", uiState.project?.name)
    }

    @Test
    fun givenComponentResumedWhenProjectNotEmptyThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        runOnUiThread {
            lifecycle.resume()
        }

        val uiState = sut.uiState.value
        assertEquals(1, uiState.unitCount)
        assertEquals("test", uiState.project?.name)
    }

    @Test
    fun givenComponentResumedWhenCloseDialogThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        runOnUiThread {
            lifecycle.resume()
        }

        runOnUiThread {
            sut.reduce(TranslateComponent.Intent.CloseDialog)
        }
        sut.dialog.configAsFlow<TranslateComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<TranslateComponent.DialogConfig.None>(item)
        }
    }

    @Test
    fun givenComponentResumedWhenAddSegmentThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        runOnUiThread {
            lifecycle.resume()
        }

        runOnUiThread {
            sut.reduce(TranslateComponent.Intent.AddSegment)
        }

        sut.dialog.configAsFlow<TranslateComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<TranslateComponent.DialogConfig.NewSegment>(item)
        }
    }

    @Test
    fun givenComponentResumedWhenSaveThenLogicIsCalled() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        coEvery { mockProjectRepository.getById(any()) } returns ProjectModel()
        coEvery { mockRecentProjectRepository.getByName(any()) } returns RecentProjectModel()
        coEvery { mockSaveProject.invoke(any(), any()) } returns Unit
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.Save("path"))

        coVerify { mockSaveProject.invoke(any(), "path") }
    }

    @Test
    fun givenComponentResumedWhenImportAndroidThenLogicIsCalled() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel(), SegmentModel())
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockImportResources.invoke(any(), any()) } returns listOf(
            SegmentModel(key = "key 1", text = "text 1"),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery {
            mockImportSegments.invoke(
                any(),
                any(),
                any(),
            )
        } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.Import("path", ResourceFileType.ANDROID_XML))

        val uiState = sut.uiState.value
        assertEquals(2, uiState.unitCount)
        coVerify { mockImportResources.invoke("path", ResourceFileType.ANDROID_XML) }
    }

    @Test
    fun givenComponentResumedWhenExportAndroidThenLogicIsCalled() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockExportResources.invoke(any(), any(), any(), any()) } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.Export("path", ResourceFileType.ANDROID_XML))

        coVerify {
            mockExportResources.invoke(
                segments = any(),
                path = "path",
                lang = "en",
                type = ResourceFileType.ANDROID_XML,
            )
        }
    }

    @Test
    fun givenComponentResumedWhenTogglePanelThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.TogglePanel(TranslateComponent.PanelConfig.Validation))

        sut.panel.configAsFlow<TranslateComponent.PanelConfig>().test {
            val item = awaitItem()
            assertEquals(TranslateComponent.PanelConfig.Validation, item)
        }
    }

    @Test
    fun givenComponentResumedWhenExportTmxThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockExportToTmx.invoke(any(), any()) } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.ExportTmx("path"))

        coVerify { mockExportToTmx.invoke("en", "path") }
    }

    @Test
    fun givenComponentResumedWhenSyncWithTmThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSyncProjectWithTm.invoke(any()) } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.SyncWithTm)

        coVerify { mockSyncProjectWithTm.invoke(any()) }
    }

    @Test
    fun givenComponentResumedWhenValidatePlaceholdersThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockValidatePlaceholders.invoke(any()) } returns ValidatePlaceholdersUseCase.Output.Valid
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.ValidatePlaceholders)

        coVerify { mockValidatePlaceholders.invoke(any()) }
    }

    @Test
    fun givenComponentResumedWhenGlobalSpellcheckPlaceholdersThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockValidateSpelling.invoke(any(), any()) } returns emptyMap()
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.GlobalSpellcheck)

        coVerify { mockValidateSpelling.invoke(any(), any()) }
    }

    @Test
    fun givenComponentResumedWhenContributeTmPlaceholdersThenConfigChangesAccordingly() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockSegmentRepository.getAll(any()) } returns listOf(SegmentModel())
        coEvery { mockSegmentRepository.getUntranslatable(any()) } returns emptyList()
        coEvery { mockProjectRepository.observeById(any()) } returns flowOf(ProjectModel(name = "test"))
        coEvery { mockProjectRepository.observeNeedsSaving() } returns flowOf(false)
        coEvery { mockProjectRepository.getById(any()) } returns ProjectModel()
        MockFileManager.setup("test", ".tmx")
        coEvery { mockRecentProjectRepository.getByName(any()) } returns RecentProjectModel(path = MockFileManager.getFilePath())
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery {
            mockMachineTranslationRepository.importTm(
                file = any(),
                key = any(),
                private = any(),
                name = any(),
                subject = any(),
            )
        } returns Unit
        runOnUiThread {
            lifecycle.resume()
        }

        sut.reduce(TranslateComponent.Intent.MachineTranslationContributeTm)

        coVerify {
            mockMachineTranslationRepository.importTm(
                file = any(),
                key = "key",
                private = any(),
                name = any(),
                subject = any(),
            )
        }
        MockFileManager.teardown()
    }
}
