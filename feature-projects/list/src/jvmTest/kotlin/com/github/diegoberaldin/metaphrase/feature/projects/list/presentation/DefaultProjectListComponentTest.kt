package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class DefaultProjectListComponentTest {

    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    localizationModule,
                )
            }
            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockProjectRepository = mockk<RecentProjectRepository>()
    private val mockNotificationCenter = mockk<NotificationCenter>()
    private val mockOpenProjectUseCase = mockk<OpenProjectUseCase>()
    private val sut = DefaultProjectListComponent(
        dispatchers = MockCoroutineDispatcherProvider,
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        projectRepository = mockProjectRepository,
        notificationCenter = mockNotificationCenter,
        openProject = mockOpenProjectUseCase,
    )

    init {
        setup
    }

    @Test
    fun givenComponentResumedWhenListEmptyThenNoProjectsAreDisplayed() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        lifecycle.resume()

        sut.uiState.test {
            val item = awaitItem()
            assertEquals(0, item.projects.size)
        }
    }

    /*@Test
    fun givenComponentResumedWhenListNotEmptyThenProjectsAreDisplayed() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        lifecycle.resume()
        sut.uiState.test {
            val item = awaitItem()
            assertEquals(1, item.projects.size)
        }
    }*/

    @Test
    fun givenComponentResumedWhenCloseDialogTheChildSlotChangesAccordingly() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        lifecycle.resume()

        runOnUiThread {
            sut.closeDialog()
        }
        sut.dialog.configAsFlow<ProjectListComponent.DialogConfiguration>().test {
            val item = awaitItem()
            assertIs<ProjectListComponent.DialogConfiguration.None>(item)
        }
    }

    @Test
    fun givenComponentResumedWhenOpenRecentThenProjectIsSelected() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockOpenProjectUseCase.invoke(any()) } returns ProjectModel(name = "test")
        lifecycle.resume()

        launch {
            sut.openRecent(RecentProjectModel(name = "test", path = "path"))
        }

        sut.projectSelected.test {
            val item = awaitItem()
            assertEquals("test", item.name)
        }
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
        coVerify { mockOpenProjectUseCase.invoke("path") }
    }

    @Test
    fun givenComponentResumedAndNonExistingProjectWhenOpenRecentThenDialogIsDisplayed() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        coEvery { mockProjectRepository.delete(any()) } returns Unit
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockOpenProjectUseCase.invoke(any()) } returns null
        lifecycle.resume()

        launch {
            sut.openRecent(RecentProjectModel(name = "test", path = "path"))
        }

        sut.dialog.configAsFlow<ProjectListComponent.DialogConfiguration>().test {
            val item = awaitItem()
            assertIs<ProjectListComponent.DialogConfiguration.OpenError>(item)
        }
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
        coVerify { mockOpenProjectUseCase.invoke("path") }
        coVerify { mockProjectRepository.delete(withArg { assertEquals("path", it.path) }) }
    }

    @Test
    fun givenComponentResumedWhenRemoveFromRecentThenProjectIsDeleted() = runTest {
        coEvery { mockProjectRepository.observeAll() } returns flowOf(emptyList())
        coEvery { mockProjectRepository.delete(any()) } returns Unit
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        lifecycle.resume()

        sut.removeFromRecent(RecentProjectModel(name = "test"))

        coVerify { mockProjectRepository.delete(withArg { assertEquals("test", it.name) }) }
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
}
