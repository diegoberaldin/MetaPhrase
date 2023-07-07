package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultTranslateToolbarComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val sut = DefaultTranslateToolbarComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        completeLanguage = mockCompleteLanguage,
    )

    @Test
    fun givenComponentCreatedWhenSetProjectIdThenStateIsPopulated() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()

        sut.projectId = 1

        val uiState = sut.uiState.value
        assertEquals(2, uiState.availableLanguages.size)
        assertEquals(3, uiState.availableFilters.size)
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
        assertEquals("", uiState.currentSearch)
        assertEquals(TranslationUnitTypeFilter.ALL, uiState.currentTypeFilter)
        assertFalse(uiState.isEditing)
    }

    @Test
    fun givenComponentCreatedWhenSetEditingThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.reduce(TranslateToolbarComponent.Intent.SetEditing(true))

        val uiState = sut.uiState.value
        assertTrue(uiState.isEditing)
    }

    @Test
    fun givenComponentCreatedWhenSetCurrentLanguageThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.reduce(TranslateToolbarComponent.Intent.SetLanguage(LanguageModel(code = "en")))

        val uiState = sut.uiState.value
        val currentLanguage = uiState.currentLanguage
        assertNotNull(currentLanguage)
        assertEquals("en", currentLanguage.code)
    }

    @Test
    fun givenComponentCreatedWhenSetCurrentFilterThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.reduce(TranslateToolbarComponent.Intent.SetTypeFilter(TranslationUnitTypeFilter.TRANSLATABLE))

        val uiState = sut.uiState.value
        assertEquals(TranslationUnitTypeFilter.TRANSLATABLE, uiState.currentTypeFilter)
    }

    @Test
    fun givenComponentCreatedWhenSetSearchThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.reduce(TranslateToolbarComponent.Intent.SetSearch("test"))

        val uiState = sut.uiState.value
        assertEquals("test", uiState.currentSearch)
    }

    @Test
    fun givenComponentCreatedWhenOnSearchFiredThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.reduce(TranslateToolbarComponent.Intent.SetSearch("test"))

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.OnSearchFired)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.Search>(item)
            assertEquals("test", item.text)
        }
    }

    @Test
    fun givenComponentCreatedWhenCopyBaseThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1
        sut.reduce(TranslateToolbarComponent.Intent.SetLanguage(LanguageModel(code = "it")))

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.CopyBase)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.CopyBase>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenMoveToPreviousThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.MoveToPrevious)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.MoveToPrevious>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenMoveToNextThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.MoveToNext)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.MoveToNext>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenAddUnitThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.AddUnit)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.AddUnit>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenRemoveUnitThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.RemoveUnit)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.RemoveUnit>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenValidateUnitsThenEventIsEmitted() = runTest {
        coEvery { mockLanguageRepository.observeAll(any()) } returns flowOf(
            listOf(
                LanguageModel(code = "en", isBase = true),
                LanguageModel(code = "it"),
            ),
        )
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        lifecycle.create()
        sut.projectId = 1
        sut.reduce(TranslateToolbarComponent.Intent.SetLanguage(LanguageModel(code = "it")))

        sut.effects.test {
            sut.reduce(TranslateToolbarComponent.Intent.ValidateUnits)
            val item = awaitItem()
            assertIs<TranslateToolbarComponent.Effect.ValidateUnits>(item)
        }
    }
}
