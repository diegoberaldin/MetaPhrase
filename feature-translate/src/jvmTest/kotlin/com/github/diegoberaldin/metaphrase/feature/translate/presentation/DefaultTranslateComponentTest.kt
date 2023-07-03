package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
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
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals

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
        runOnUiThread {
            lifecycle.resume()
        }

        val uiState = sut.uiState.value
        assertEquals(1, uiState.unitCount)
        assertEquals("test", uiState.project?.name)
    }
}
