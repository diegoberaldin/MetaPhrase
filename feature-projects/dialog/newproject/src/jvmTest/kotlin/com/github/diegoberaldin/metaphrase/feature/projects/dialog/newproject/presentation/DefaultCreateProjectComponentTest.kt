package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultCreateProjectComponentTest {
    companion object {
        private val setup by lazy {
            startKoin {
                modules(localizationModule)
            }

            L10n.setLanguage("en")
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val sut = DefaultCreateProjectComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        projectRepository = mockProjectRepository,
        segmentRepository = mockSegmentRepository,
        completeLanguage = mockCompleteLanguage,
    )

    init {
        setup
    }

    @Test
    fun givenComponentCreatedWithoutProjectWhenFirstLoadedThenStateIsEmpty() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        val uiState = sut.uiState.value
        assertEquals(2, uiState.availableLanguages.size)
        assertEquals("en", uiState.availableLanguages.first().code)
        assertTrue(uiState.languages.isEmpty())
        assertEquals("", uiState.name)
    }

    @Test
    fun givenComponentCreatedWhenSetNameThenStateIsUpdated() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.setName("test")

        val uiState = sut.uiState.value
        assertEquals("test", uiState.name)
    }

    @Test
    fun givenComponentCreatedWhenAddLanguageThenStateIsUpdated() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.addLanguage(LanguageModel(code = "en"))

        val uiState = sut.uiState.value
        assertFalse(uiState.languages.isEmpty())
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyDataThenStateIsError() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.nameError)
        assertEquals("message_select_one_language".localized(), uiState.languagesError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyNameThenStateIsError() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.addLanguage(LanguageModel(code = "en"))
        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("message_missing_field".localized(), uiState.nameError)
        assertEquals("", uiState.languagesError)
    }

    @Test
    fun givenComponentCreatedWhenSubmitWithEmptyLanguagesThenStateIsError() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.setName("test")
        sut.submit()

        val uiState = sut.uiState.value
        assertEquals("", uiState.nameError)
        assertEquals("message_select_one_language".localized(), uiState.languagesError)
    }

    @Test
    fun givenComponentCreatedWhenRemoveLanguageThenStateIsUpdated() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.addLanguage(LanguageModel(code = "en"))
        sut.addLanguage(LanguageModel(code = "it"))

        val stateBefore = sut.uiState.value
        assertEquals(2, stateBefore.languages.size)

        sut.removeLanguage(LanguageModel(code = "it"))

        val stateAfter = sut.uiState.value
        assertEquals(1, stateAfter.languages.size)
    }

    @Test
    fun givenComponentCreatedWhenChangeBaseLanguageThenStateIsUpdated() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.addLanguage(LanguageModel(code = "en"))
        sut.addLanguage(LanguageModel(code = "it"))

        val stateBefore = sut.uiState.value
        assertEquals(2, stateBefore.languages.size)
        assertTrue(stateBefore.languages.first().isBase)
        assertFalse(stateBefore.languages[1].isBase)

        sut.setBaseLanguage(LanguageModel(code = "it"))

        val stateAfter = sut.uiState.value
        assertEquals(2, stateAfter.languages.size)
        assertFalse(stateAfter.languages.first().isBase)
        assertTrue(stateAfter.languages[1].isBase)
    }

    @Test
    fun givenComponentCreatedWhenSubmitNewProjectThenProjectIsCreated() = runTest {
        every { mockLanguageRepository.getDefaultLanguages() } returns listOf(
            LanguageModel(code = "en"),
            LanguageModel(code = "it"),
        )
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockProjectRepository.create(any()) } returns 1
        coEvery { mockProjectRepository.getById(any()) } returns null
        coEvery { mockLanguageRepository.getAll(any()) } returns emptyList()
        coEvery { mockLanguageRepository.create(any(), any()) } returns 1
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getByCode(any(), any()) } returns null
        lifecycle.create()

        sut.setName("test")
        sut.addLanguage(LanguageModel(code = "en"))

        sut.done.test {
            sut.submit()
            val item = awaitItem()
            assertNotNull(item)
        }

        val state = sut.uiState.value
        assertEquals("", state.nameError)
        assertEquals("", state.languagesError)
        assertFalse(state.isLoading)
    }
}
