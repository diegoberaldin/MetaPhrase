package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.GetSimilaritiesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultTranslationMemoryComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockGetSimilarities = mockk<GetSimilaritiesUseCase>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val sut = DefaultTranslationMemoryComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        getSimilarities = mockGetSimilarities,
        segmentRepository = mockSegmentRepository,
        keyStore = mockKeyStore,
    )

    @Test
    fun givenComponentCreatedWhenLoadThenUnitsAreRetrieved() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "test")
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 80
        coEvery {
            mockGetSimilarities.invoke(
                segment = any(),
                projectId = any(),
                languageId = any(),
                threshold = any(),
            )
        } returns listOf(
            TranslationUnit(
                segment = SegmentModel(key = "key", text = "prova"),
                original = SegmentModel(key = "key", text = "test"),
                similarity = 100,
            ),
        )
        lifecycle.create()

        sut.reduce(TranslationMemoryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        val uiState = sut.uiState.value
        assertEquals(1, uiState.units.size)
        coVerify {
            mockGetSimilarities.invoke(
                segment = withArg { assertEquals("key", it.key) },
                projectId = 1,
                languageId = 1,
                threshold = 0.8f,
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenClearThenUnitsAreRemoved() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "test")
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 80
        coEvery {
            mockGetSimilarities.invoke(
                segment = any(),
                projectId = any(),
                languageId = any(),
                threshold = any(),
            )
        } returns listOf(
            TranslationUnit(
                segment = SegmentModel(key = "key", text = "prova"),
                original = SegmentModel(key = "key", text = "test"),
                similarity = 100,
            ),
        )
        lifecycle.create()
        sut.reduce(TranslationMemoryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(TranslationMemoryComponent.ViewIntent.Clear)

        val uiState = sut.uiState.value
        assertEquals(0, uiState.units.size)
    }

    @Test
    fun givenComponentCreatedWhenCopyTranslationThenEventIsFired() = runTest {
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "test")
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 80
        coEvery {
            mockGetSimilarities.invoke(
                segment = any(),
                projectId = any(),
                languageId = any(),
                threshold = any(),
            )
        } returns listOf(
            TranslationUnit(
                segment = SegmentModel(key = "key", text = "prova"),
                original = SegmentModel(key = "key", text = "test"),
                similarity = 100,
            ),
        )
        lifecycle.create()
        sut.reduce(TranslationMemoryComponent.ViewIntent.Load(key = "key", projectId = 1, languageId = 1))

        sut.effects.test {
            sut.reduce(TranslationMemoryComponent.ViewIntent.CopyTranslation(0))
            val item = awaitItem()
            assertIs<TranslationMemoryComponent.Effect.Copy>(item)
            assertEquals("prova", item.value)
        }
    }
}
