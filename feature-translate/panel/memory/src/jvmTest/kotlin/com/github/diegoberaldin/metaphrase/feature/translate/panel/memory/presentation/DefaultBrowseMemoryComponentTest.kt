package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DefaultBrowseMemoryComponentTest {
    private val lifecycle = LifecycleRegistry()
    private val mockMemoryEntryRepository = mockk<MemoryEntryRepository>()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val sut = DefaultBrowseMemoryComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        memoryEntryRepository = mockMemoryEntryRepository,
        completeLanguage = mockCompleteLanguage,
    )

    @Test
    fun givenComponentCreatedWhenSetLanguagesThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        lifecycle.create()

        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        val uiState = sut.uiState.value
        assertEquals("en", uiState.sourceLanguage?.code)
        assertEquals("it", uiState.targetLanguage?.code)
        assertTrue(uiState.availableSourceLanguages.none { it.code == "it" })
        assertTrue(uiState.availableTargetLanguages.none { it.code == "en" })
        assertEquals("", uiState.currentSearch)
        assertEquals(0, uiState.entries.size)
    }

    @Test
    fun givenComponentCreatedWhenEntriesArePresentThenStateIsPopulated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns listOf(
            TranslationMemoryEntryModel(
                identifier = "key",
                sourceLang = "en",
                sourceText = "test",
                targetLang = "it",
                targetText = "prova",
            ),
        )
        lifecycle.create()

        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        val uiState = sut.uiState.value
        assertEquals("en", uiState.sourceLanguage?.code)
        assertEquals("it", uiState.targetLanguage?.code)
        assertTrue(uiState.availableSourceLanguages.none { it.code == "it" })
        assertTrue(uiState.availableTargetLanguages.none { it.code == "en" })
        assertEquals("", uiState.currentSearch)
        assertEquals(1, uiState.entries.size)
        assertEquals("key", uiState.entries.first().identifier)
        coVerify {
            mockMemoryEntryRepository.getEntries(
                sourceLang = withArg { assertEquals("en", it) },
                targetLang = withArg { assertEquals("it", it) },
                search = "",
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenSetSourceLanguageThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        lifecycle.create()
        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        sut.setSourceLanguage(LanguageModel(code = "it"))

        val uiState = sut.uiState.value
        assertEquals("it", uiState.sourceLanguage?.code)
        assertTrue(uiState.availableSourceLanguages.any { it.code == "it" })
        assertEquals("", uiState.currentSearch)
    }

    @Test
    fun givenComponentCreatedWhenSetTargetLanguageThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        lifecycle.create()
        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        sut.setTargetLanguage(LanguageModel(code = "es"))

        val uiState = sut.uiState.value
        assertEquals("es", uiState.targetLanguage?.code)
        assertTrue(uiState.availableTargetLanguages.any { it.code == "es" })
        assertEquals("", uiState.currentSearch)
    }

    @Test
    fun givenComponentCreatedWhenSearchThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        lifecycle.create()
        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        sut.setSearch("search")

        val uiState = sut.uiState.value
        assertEquals("search", uiState.currentSearch)
    }

    @Test
    fun givenComponentCreatedWhenOnSearchFiredThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns listOf(
            TranslationMemoryEntryModel(
                identifier = "key",
                sourceLang = "en",
                sourceText = "test",
                targetLang = "it",
                targetText = "prova",
            ),
        )
        lifecycle.create()
        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))
        sut.setSearch("search")

        sut.onSearchFired()

        coVerify {
            mockMemoryEntryRepository.getEntries(
                sourceLang = withArg { assertEquals("en", it) },
                targetLang = withArg { assertEquals("it", it) },
                search = "search",
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenDeleteEntryThenStateIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        coEvery { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.name)
        }
        coEvery { mockMemoryEntryRepository.getLanguageCodes() } returns listOf("en", "it", "es")
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns emptyList()
        coEvery { mockMemoryEntryRepository.getEntries(any(), any(), any()) } returns listOf(
            TranslationMemoryEntryModel(
                identifier = "key",
                sourceLang = "en",
                sourceText = "test",
                targetLang = "it",
                targetText = "prova",
            ),
        )
        coEvery { mockMemoryEntryRepository.delete(any()) } returns Unit
        lifecycle.create()
        sut.setLanguages(LanguageModel(code = "en"), LanguageModel(code = "it"))

        sut.deleteEntry(0)

        coVerify { mockMemoryEntryRepository.delete(withArg { assertEquals("key", it.identifier) }) }
    }
}
