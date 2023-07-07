package com.github.diegoberaldin.metaphrase.feature.projects.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.activeConfigAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.persistence.di.persistenceModule
import com.github.diegoberaldin.metaphrase.domain.language.di.languageModule
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.di.projectModule
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.OpenProjectUseCase
import com.github.diegoberaldin.metaphrase.feature.projects.di.projectsModule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DefaultProjectsComponentTest {
    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    commonModule,
                    localizationModule,
                    persistenceModule,
                    languageModule,
                    projectModule,
                    projectsModule,
                )
            }
            L10n.setLanguage("en")
        }
    }

    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockRecentProjectRepository = mockk<RecentProjectRepository>()
    private val mockOpenProjectUseCase = mockk<OpenProjectUseCase>()
    private val lifecycle = LifecycleRegistry()
    private val sut: DefaultProjectsComponent

    init {
        setup

        sut = DefaultProjectsComponent(
            dispatchers = MockCoroutineDispatcherProvider,
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            coroutineContext = TestScope().coroutineContext,
            keyStore = mockKeyStore,
            projectRepository = mockProjectRepository,
            recentProjectRepository = mockRecentProjectRepository,
            openProjectUseCase = mockOpenProjectUseCase,
        )
    }

    @Test
    fun givenComponentCreatedWhenOpenProjectThenActiveProjectIsUpdateAndNavigationChangesAccordingly() = runTest {
        val idSlot = slot<Int>()
        coEvery { mockProjectRepository.getById(capture(idSlot)) } answers {
            ProjectModel(
                id = idSlot.captured,
                name = "${idSlot.captured}",
            )
        }
        coEvery { mockKeyStore.save(KeyStoreKeys.LastOpenedProject, any<String>()) } returns Unit
        lifecycle.create()

        val projectId = 1
        runOnUiThread {
            sut.reduce(ProjectsComponent.Intent.Open(projectId))
        }

        val uiState = sut.uiState.value
        assertNotNull(uiState.activeProject)
        assertEquals(projectId, uiState.activeProject?.id)

        sut.childStack.activeConfigAsFlow<ProjectsComponent.Config>().test {
            val item = awaitItem()
            assertIs<ProjectsComponent.Config.Detail>(item)
            assertEquals(projectId, item.projectId)
        }
        coVerify { mockKeyStore.save(KeyStoreKeys.LastOpenedProject, "1") }
    }

    @Test
    fun givenComponentCreatedWhenCloseCurrentProjectThenActiveProjectIsUpdateAndNavigationChangesAccordingly() =
        runTest {
            val idSlot = slot<Int>()
            coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
            coEvery { mockProjectRepository.getById(capture(idSlot)) } answers { ProjectModel(id = idSlot.captured) }
            coEvery { mockKeyStore.save(KeyStoreKeys.LastOpenedProject, any<String>()) } returns Unit
            lifecycle.create()

            runOnUiThread {
                sut.reduce(ProjectsComponent.Intent.CloseCurrentProject)
            }

            val uiState = sut.uiState.value
            assertNull(uiState.activeProject)
            sut.childStack.activeConfigAsFlow<ProjectsComponent.Config>().test {
                val item = awaitItem()
                assertIs<ProjectsComponent.Config.List>(item)
            }
            coVerify { mockKeyStore.save(KeyStoreKeys.LastOpenedProject, "") }
        }
}
