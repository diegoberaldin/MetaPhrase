package com.github.diegoberaldin.metaphrase.feature.main.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.persistence.di.persistenceModule
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ClearGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ExportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ImportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.di.projectModule
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ClearTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ImportTmxUseCase
import com.github.diegoberaldin.metaphrase.feature.main.di.mainModule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class DefaultRootComponentTest {

    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    commonModule,
                    localizationModule,
                    persistenceModule,
                    languageModule,
                    projectModule,
                    mainModule,
                )
            }
            L10n.setLanguage("en")
        }
    }

    private val mockOpenProjectUseCase = mockk<OpenProjectUseCase>()
    private val mockRecentProjectRepository = mockk<RecentProjectRepository>()
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockImportTmxUseCase = mockk<ImportTmxUseCase>()
    private val mockClearGlossaryTerms = mockk<ClearGlossaryUseCase>()
    private val mockClearTranslationMemory = mockk<ClearTmUseCase>()
    private val mockImportGlossaryTerms = mockk<ImportGlossaryUseCase>()
    private val mockExportGlossaryTerms = mockk<ExportGlossaryUseCase>()
    private val mockNotificationCenter = mockk<NotificationCenter>(relaxUnitFun = true)
    private val lifecycle = LifecycleRegistry()
    private val sut = DefaultRootComponent(
        dispatchers = MockCoroutineDispatcherProvider,
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        recentProjectRepository = mockRecentProjectRepository,
        projectRepository = mockProjectRepository,
        importFromTmx = mockImportTmxUseCase,
        clearGlossaryTerms = mockClearGlossaryTerms,
        clearTranslationMemory = mockClearTranslationMemory,
        importGlossaryTerms = mockImportGlossaryTerms,
        exportGlossaryTerms = mockExportGlossaryTerms,
        openProjectUseCase = mockOpenProjectUseCase,
        notificationCenter = mockNotificationCenter,
    )

    init {
        setup
    }

    @Test
    fun givenComponentCreatedWhenHasUnsavedChangesThenProjectRepositoryIsQueried() {
        lifecycle.create()
        coEvery { mockProjectRepository.isNeedsSaving() } returns true
        val res = sut.hasUnsavedChanges()
        assertTrue(res)
        coVerify { mockProjectRepository.isNeedsSaving() }
    }

    @Test
    fun givenComponentCreatedWhenOpenProjectThenMainConfigChangesAccordinglyAndRecentProjectIsCreatedIfNeeded() =
        runTest {
            coEvery { mockRecentProjectRepository.getByName(any()) } returns null
            coEvery { mockRecentProjectRepository.create(any()) } returns 1
            coEvery { mockOpenProjectUseCase.invoke(any()) } returns ProjectModel()
            lifecycle.create()

            runOnUiThread {
                sut.reduce(RootComponent.ViewIntent.OpenProject("path"))
            }

            sut.main.configAsFlow<RootComponent.Config>().test {
                val item = awaitItem()
                assertIs<RootComponent.Config.Projects>(item)
            }
            coVerifyOrder {
                mockRecentProjectRepository.getByName(any())
                mockRecentProjectRepository.create(any())
            }
            coVerify { mockOpenProjectUseCase.invoke("path") }
            coVerifyOrder {
                mockNotificationCenter.send(
                    withArg {
                        assertIs<NotificationCenter.Event.ShowProgress>(it)
                        assertTrue(it.visible)
                    },
                )
                mockNotificationCenter.send(
                    withArg {
                        assertIs<NotificationCenter.Event.ShowProgress>(it)
                        assertFalse(it.visible)
                    },
                )
            }
        }

    @Test
    fun givenComponentCreatedWhenOpenDialogThenDialogConfigChangesAccordingly() = runTest {
        every { mockProjectRepository.isNeedsSaving() } returns false
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.OpenDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenNewDialogThenDialogConfigChangesAccordingly() = runTest {
        every { mockProjectRepository.isNeedsSaving() } returns false
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenNewDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.NewDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenCloseDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.CloseDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.None>(item)
        }
    }

    @Test
    fun givenComponentCreatedAndProjectNeedsSavingWhenCloseCurrentProjectThenDialogConfigChangesToAskConfirmation() =
        runTest {
            lifecycle.create()
            coEvery { mockProjectRepository.isNeedsSaving() } returns true

            runOnUiThread {
                sut.reduce(RootComponent.ViewIntent.CloseCurrentProject(closeAfter = true))
            }

            sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
                val item = awaitItem()
                assertIs<RootComponent.DialogConfig.ConfirmCloseDialog>(item)
                assertTrue(item.closeAfter)
            }
        }

    @Test
    fun givenComponentCreatedWhenImportDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenImportDialog(ResourceFileType.ANDROID_XML))
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ImportDialog>(item)
            assertEquals(ResourceFileType.ANDROID_XML, item.type)
        }
    }

    @Test
    fun givenComponentCreatedWhenExportDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenExportDialog(ResourceFileType.ANDROID_XML))
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ExportDialog>(item)
            assertEquals(ResourceFileType.ANDROID_XML, item.type)
        }
    }

    @Test
    fun givenComponentCreatedWhenOpenStatisticsDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenStatistics)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.StatisticsDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenOpenSettingsDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenSettings)
        }

        delay(100)
        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.SettingsDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenExportTmxDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenExportTmxDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ExportTmxDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenImportTmxDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenImportTmxDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ImportTmxDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenImportTmxThenUseCaseInInvoked() {
        coEvery { mockImportTmxUseCase.invoke(any()) } returns Unit
        lifecycle.create()

        sut.reduce(RootComponent.ViewIntent.ImportTmx("path"))

        coVerify { mockImportTmxUseCase.invoke("path") }
        coVerifyOrder {
            mockNotificationCenter.send(
                withArg {
                    assertIs<NotificationCenter.Event.ShowProgress>(it)
                    assertTrue(it.visible)
                },
            )
            mockNotificationCenter.send(
                withArg {
                    assertIs<NotificationCenter.Event.ShowProgress>(it)
                    assertFalse(it.visible)
                },
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenClearTmThenUseCaseInInvoked() {
        coEvery { mockClearTranslationMemory.invoke() } returns Unit
        lifecycle.create()

        sut.reduce(RootComponent.ViewIntent.ClearTm)

        coVerify { mockClearTranslationMemory.invoke() }
        coVerifyOrder {
            mockNotificationCenter.send(
                withArg {
                    assertIs<NotificationCenter.Event.ShowProgress>(it)
                    assertTrue(it.visible)
                },
            )
            mockNotificationCenter.send(
                withArg {
                    assertIs<NotificationCenter.Event.ShowProgress>(it)
                    assertFalse(it.visible)
                },
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenImportGlossaryDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenImportGlossaryDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ImportGlossaryDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenImportGlossaryThenUseCaseInInvoked() {
        coEvery { mockImportGlossaryTerms.invoke(any()) } returns Unit
        lifecycle.create()

        sut.reduce(RootComponent.ViewIntent.ImportGlossary("path"))

        coVerify { mockImportGlossaryTerms.invoke("path") }
    }

    @Test
    fun givenComponentCreatedWhenExportGlossaryDialogThenDialogConfigChangesAccordingly() = runTest {
        lifecycle.create()

        runOnUiThread {
            sut.reduce(RootComponent.ViewIntent.OpenExportGlossaryDialog)
        }

        sut.dialog.configAsFlow<RootComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<RootComponent.DialogConfig.ExportGlossaryDialog>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenExportGlossaryThenUseCaseInInvoked() {
        coEvery { mockExportGlossaryTerms.invoke(any()) } returns Unit
        lifecycle.create()

        sut.reduce(RootComponent.ViewIntent.ExportGlossary("path"))

        coVerify { mockExportGlossaryTerms.invoke("path") }
    }

    @Test
    fun givenComponentCreatedWhenClearGlossaryThenUseCaseInInvoked() {
        coEvery { mockClearGlossaryTerms.invoke() } returns Unit
        lifecycle.create()

        sut.reduce(RootComponent.ViewIntent.ClearGlossary)

        coVerify { mockClearGlossaryTerms.invoke() }
    }
}
