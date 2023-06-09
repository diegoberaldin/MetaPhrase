package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals

class DefaultMachineTranslationComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockMachineTranslationRepository = mockk<MachineTranslationRepository>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val sut = DefaultMachineTranslationComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        languageRepository = mockLanguageRepository,
        segmentRepository = mockSegmentRepository,
        machineTranslationRepository = mockMachineTranslationRepository,
        keyStore = mockKeyStore,
    )

    @Test
    fun givenComponentCreatedAndContentLoadedWhenSuggestionRetrievedThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery {
            mockMachineTranslationRepository.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns "suggestion"
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()

        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))
        sut.reduce(MachineTranslationComponent.Intent.Retrieve)

        val uiState = sut.uiState.value
        assertEquals("suggestion", uiState.translation)
        coVerify {
            mockMachineTranslationRepository.getTranslation(
                key = "key",
                sourceMessage = "text",
                sourceLang = "en",
                targetLang = "it",
            )
        }
    }

    @Test
    fun givenComponentCreatedAndContentLoadedWhenSetTranslationThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery {
            mockMachineTranslationRepository.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns "suggestion"
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()
        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(MachineTranslationComponent.Intent.SetTranslation("translation"))

        val uiState = sut.uiState.value
        assertEquals("translation", uiState.translation)
    }

    @Test
    fun givenComponentCreatedAndContentLoadedWhenCopyTargetThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery {
            mockMachineTranslationRepository.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns "suggestion"
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()
        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))

        sut.effects.test {
            sut.reduce(MachineTranslationComponent.Intent.CopyTarget)
            val item = awaitItem()
            assertIs<MachineTranslationComponent.Effect.CopyTarget>(item)
        }
    }

    @Test
    fun givenComponentCreatedAndContentLoadedWhenCopyTranslationThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery {
            mockMachineTranslationRepository.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns "suggestion"
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()
        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))
        val stateBefore = sut.uiState.value

        sut.reduce(MachineTranslationComponent.Intent.CopyTranslation("translation"))

        val stateAfter = sut.uiState.value
        assertNotEquals(stateAfter.updateTextSwitch, stateBefore.updateTextSwitch)
        assertEquals("translation", stateAfter.translation)
    }

    @Test
    fun givenComponentCreatedAndContentLoadedWhenInsertTranslationThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery {
            mockMachineTranslationRepository.getTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
            )
        } returns "suggestion"
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()
        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))

        sut.reduce(MachineTranslationComponent.Intent.SetTranslation("translation"))

        sut.effects.test {
            sut.reduce(MachineTranslationComponent.Intent.InsertTranslation)
            val item = awaitItem()
            assertIs<MachineTranslationComponent.Effect.CopySource>(item)
            assertEquals("translation", item.value)
        }
    }

    @Test
    fun givenComponentCreatedAndContentLoadedWhenShareThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getById(any()) } returns LanguageModel(code = "it")
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(text = "text")
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery {
            mockMachineTranslationRepository.shareTranslation(
                key = any(),
                sourceMessage = any(),
                sourceLang = any(),
                targetLang = any(),
                targetMessage = any(),
            )
        } returns Unit
        every { mockMachineTranslationRepository.supportsContributions } returns MutableStateFlow(true)
        lifecycle.create()
        sut.reduce(MachineTranslationComponent.Intent.Load(key = "key", projectId = 1, languageId = 1))
        sut.reduce(MachineTranslationComponent.Intent.SetTranslation("translation"))

        sut.effects.test {
            sut.reduce(MachineTranslationComponent.Intent.Share)
            val item = awaitItem()
            assertIs<MachineTranslationComponent.Effect.Share>(item)
        }

        coVerify {
            mockMachineTranslationRepository.shareTranslation(
                key = "key",
                sourceMessage = "text",
                sourceLang = "en",
                targetLang = "it",
                targetMessage = "translation",
            )
        }
    }
}
