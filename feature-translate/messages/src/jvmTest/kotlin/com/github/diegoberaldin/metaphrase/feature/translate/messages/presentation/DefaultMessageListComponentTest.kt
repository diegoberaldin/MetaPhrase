package com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DefaultMessageListComponentTest {

    private val lifecycle = LifecycleRegistry()
    private val mockProjectRepository = mockk<ProjectRepository>()
    private val mockSegmentRepository = mockk<SegmentRepository>()
    private val mockLanguageRepository = mockk<LanguageRepository>()
    private val mockSpellCheckRepository = mockk<SpellCheckRepository>()
    private val mockNotificationCenter = mockk<NotificationCenter>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val sut = DefaultMessageListComponent(
        componentContext = DefaultComponentContext(lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        projectRepository = mockProjectRepository,
        segmentRepository = mockSegmentRepository,
        languageRepository = mockLanguageRepository,
        spellCheckRepository = mockSpellCheckRepository,
        notificationCenter = mockNotificationCenter,
        keyStore = mockKeyStore,
    )

    @Test
    fun givenComponentCreatedWhenInitializedThenStateIsDefault() = runTest {
        lifecycle.create()

        val uiState = sut.uiState.value
        assertNull(uiState.currentLanguage)
        assertTrue(uiState.editingEnabled)
        assertNull(uiState.editingIndex)
        assertTrue(uiState.units.isEmpty())
        assertTrue(uiState.canFetchMore)
    }

    @Test
    fun givenComponentCreatedWhenSetEditingEnabledThenStateIsDefault() = runTest {
        lifecycle.create()

        sut.setEditingEnabled(false)

        val uiState = sut.uiState.value
        assertNull(uiState.currentLanguage)
        assertFalse(uiState.editingEnabled)
        assertNull(uiState.editingIndex)
        assertTrue(uiState.units.isEmpty())
        assertTrue(uiState.canFetchMore)
    }

    @Test
    fun givenComponentCreatedWhenMessagesReloadedForBaseLanguageThenMessagesAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()

        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        val uiState = sut.uiState.value
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
        assertNull(uiState.editingIndex)
        assertEquals(1, uiState.units.size)
        val unit = uiState.units.first()
        assertEquals("text", unit.segment.text)
        assertTrue(uiState.canFetchMore)
    }

    @Test
    fun givenComponentCreatedWhenMessageReloadedForOtherLanguageThenMessagesAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "testo"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()

        sut.reloadMessages(
            language = LanguageModel(code = "it"),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        val uiState = sut.uiState.value
        assertNotNull(uiState.currentLanguage)
        assertEquals("it", uiState.currentLanguage?.code)
        assertNull(uiState.editingIndex)
        assertEquals(1, uiState.units.size)
        val unit = uiState.units.first()
        assertEquals("text", unit.original?.text)
        assertEquals("testo", unit.segment.text)
        assertTrue(uiState.canFetchMore)
    }

    @Test
    fun givenComponentCreatedWhenClearMessagesLanguageThenMessagesAreCleared() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        sut.clearMessages()

        val uiState = sut.uiState.value
        assertEquals(0, uiState.units.size)
    }

    @Test
    fun givenComponentCreatedWhenSearchThenMessagesAreFiltered() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        sut.search("search")

        val uiState = sut.uiState.value
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
        assertNull(uiState.editingIndex)
        assertEquals(1, uiState.units.size)
        val unit = uiState.units.first()
        assertEquals("text", unit.segment.text)
        assertTrue(uiState.canFetchMore)
        coVerify {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = "search",
                skip = any(),
                limit = any(),
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenRefreshThenMessagesAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        sut.refresh()

        val uiState = sut.uiState.value
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
        assertNull(uiState.editingIndex)
        assertEquals(1, uiState.units.size)
        val unit = uiState.units.first()
        assertEquals("text", unit.segment.text)
        assertTrue(uiState.canFetchMore)
        coVerify(exactly = 2) {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenLoadNextPageThenMessagesAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        sut.loadNextPage()

        val uiState = sut.uiState.value
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
        assertNull(uiState.editingIndex)
        assertEquals(1, uiState.units.size)
        val unit = uiState.units.first()
        assertEquals("text", unit.segment.text)
        assertTrue(uiState.canFetchMore)
        coVerify(atLeast = 1) {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        }
    }

    @Test
    fun givenComponentCreatedWhenStartEditingThenStateIsPopulated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        sut.startEditing(0)

        val uiState = sut.uiState.value
        assertEquals(0, uiState.editingIndex)
        coEvery { mockSpellCheckRepository.check("text") }
    }

    @Test
    fun givenComponentCreatedWhenEndEditingThenStateIsPopulated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)
        val stateBefore = sut.uiState.value
        assertEquals(0, stateBefore.editingIndex)

        sut.endEditing()
        val stateAfter = sut.uiState.value
        assertNull(stateAfter.editingIndex)
    }

    @Test
    fun givenComponentCreatedWhenSetSegmentTextThenStateIsPopulated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)
        val stateBefore = sut.uiState.value
        assertEquals("text", stateBefore.units.first().segment.text)

        sut.setSegmentText("updated")
        val stateAfter = sut.uiState.value
        assertEquals("updated", stateAfter.units.first().segment.text)
        coVerify { mockProjectRepository.setNeedsSaving(true) }
    }

    @Test
    fun givenComponentCreatedWhenChangeSegmentTextThenStateIsPopulated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "text"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)
        val stateBefore = sut.uiState.value
        assertEquals("text", stateBefore.units.first().segment.text)
        val switch = stateBefore.updateTextSwitch

        sut.changeSegmentText("updated")
        val stateAfter = sut.uiState.value
        assertEquals("updated", stateAfter.units.first().segment.text)
        assertEquals(!switch, stateAfter.updateTextSwitch)
        coVerify { mockProjectRepository.setNeedsSaving(true) }
    }

    @Test
    fun givenComponentCreatedWhenMoveToPreviousThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(
            SegmentModel(key = "key 1", text = "text 1"),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(1)
        val stateBefore = sut.uiState.value
        assertEquals(1, stateBefore.editingIndex)

        launch {
            sut.moveToPrevious()
        }
        sut.selectionEvents.test {
            val item = awaitItem()
            assertEquals(0, item)
        }
    }

    @Test
    fun givenComponentCreatedWhenMoveToNextThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(
            SegmentModel(key = "key 1", text = "text 1"),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)
        val stateBefore = sut.uiState.value
        assertEquals(0, stateBefore.editingIndex)

        launch {
            sut.moveToNext()
        }
        sut.selectionEvents.test {
            val item = awaitItem()
            assertEquals(1, item)
        }
    }

    @Test
    fun givenComponentCreatedWhenCopyBaseThenMessagesAreLoaded() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(SegmentModel(key = "key", text = "testo"))
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel(key = "key", text = "text")
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns false
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "it"),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)

        sut.copyBase()

        runCatching {
            Thread.sleep(300)
        }
        val uiState = sut.uiState.value
        val unit = uiState.units.first()
        assertEquals("text", unit.original?.text)
        assertEquals("text", unit.segment.text)
        assertTrue(uiState.canFetchMore)
    }

    @Test
    fun givenComponentCreatedWhenDeleteSegmentThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(LanguageModel(code = "en", isBase = true))
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(
            SegmentModel(key = "key 1", text = "text 1"),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        coEvery { mockSegmentRepository.delete(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        sut.startEditing(0)
        val stateBefore = sut.uiState.value
        assertEquals(2, stateBefore.units.size)

        sut.deleteSegment()
        val stateAfter = sut.uiState.value
        assertEquals(1, stateAfter.units.size)
    }

    @Test
    fun givenComponentCreatedWhenScrollToMessageThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(
            SegmentModel(key = "key 1", text = "text 1"),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )

        launch {
            sut.scrollToMessage("key 2")
        }
        sut.selectionEvents.test {
            val item = awaitItem()
            assertEquals(1, item)
        }
    }

    @Test
    fun givenComponentCreatedWhenMarkAsTranslatableThenStateIsUpdated() = runTest {
        coEvery { mockLanguageRepository.getBase(any()) } returns LanguageModel(code = "en", isBase = true)
        coEvery { mockLanguageRepository.getAll(any()) } returns listOf(LanguageModel(code = "en", isBase = true))
        coEvery {
            mockSegmentRepository.search(
                languageId = any(),
                baseLanguageId = any(),
                filter = any(),
                search = any(),
                skip = any(),
                limit = any(),
            )
        } returns listOf(
            SegmentModel(key = "key 1", text = "text 1", translatable = false),
            SegmentModel(key = "key 2", text = "text 2"),
        )
        coEvery { mockSegmentRepository.getByKey(any(), any()) } returns SegmentModel()
        coEvery { mockSegmentRepository.update(any()) } returns Unit
        coEvery { mockNotificationCenter.send(any()) } returns Unit
        coEvery { mockSpellCheckRepository.setLanguage(any()) } returns Unit
        coEvery { mockSpellCheckRepository.check(any()) } returns emptyList()
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockProjectRepository.setNeedsSaving(any()) } returns Unit
        lifecycle.create()
        sut.reloadMessages(
            language = LanguageModel(code = "en", isBase = true),
            filter = TranslationUnitTypeFilter.ALL,
            projectId = 1,
        )
        val stateBefore = sut.uiState.value
        assertFalse(stateBefore.units.first().segment.translatable)

        sut.markAsTranslatable(value = true, key = "key 1")
        val stateAfter = sut.uiState.value
        assertTrue(stateAfter.units.first().segment.translatable)
    }

    @Test
    fun givenComponentCreatedWhenAddToGlossaryThenEventIsEmitted() = runTest {
        lifecycle.create()

        launch {
            sut.addToGlossarySource("term", "en")
        }
        sut.addToGlossaryEvents.test {
            val item = awaitItem()
            assertEquals("term", item.lemma)
            assertEquals("en", item.lang)
        }
    }

    @Test
    fun givenComponentCreatedWhenIgnoreWordThenEventIsEmitted() = runTest {
        coEvery { mockSpellCheckRepository.addUserDefineWord(any()) } returns Unit
        lifecycle.create()

        sut.ignoreWordInSpelling("mistake")
        coVerify { mockSpellCheckRepository.addUserDefineWord("mistake") }
    }
}
