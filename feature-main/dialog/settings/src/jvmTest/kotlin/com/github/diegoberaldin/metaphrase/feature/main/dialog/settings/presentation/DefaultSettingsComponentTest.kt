package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import app.cash.turbine.test
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.configAsFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.runOnUiThread
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultSettingsComponentTest {
    companion object {
        val setup by lazy {
            startKoin {
                modules(
                    commonModule,
                    localizationModule,
                    dialogLoginModule,
                )
            }
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val mockMachineTranslationRepository = mockk<MachineTranslationRepository>()
    private val sut = DefaultSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        completeLanguage = mockCompleteLanguage,
        keyStore = mockKeyStore,
        machineTranslationRepository = mockMachineTranslationRepository,
    )

    init {
        setup
    }

    @BeforeTest
    fun setup() {
        L10n.setLanguage("en")
    }

    @Test
    fun givenComponentCreatedWhenInitialStateThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        val uiState = sut.uiState.value
        assertEquals("70", uiState.similarityThreshold)
        assertTrue(uiState.spellcheckEnabled)
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS.first(), uiState.currentProvider)
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS, uiState.availableProviders)
        assertEquals("key", uiState.key)
        assertTrue(uiState.availableLanguages.isNotEmpty())
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
    }

    @Test
    fun givenComponentCreatedWhenSetLanguageThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        sut.setLanguage(LanguageModel(code = "it"))
        val uiState = sut.uiState.value
        assertEquals("it", uiState.currentLanguage?.code)
        coVerify { mockKeyStore.save(KeyStoreKeys.AppLanguage, "it") }
    }

    @Test
    fun givenComponentCreatedWhenSetSimilarityThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        sut.setSimilarity("80")
        val uiState = sut.uiState.value
        assertEquals("80", uiState.similarityThreshold)
        coVerify { mockKeyStore.save(KeyStoreKeys.SimilarityThreshold, 80) }
    }

    @Test
    fun givenComponentCreatedWhenSetSpellcheckEnabledThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<Boolean>()) } returns Unit
        lifecycle.create()

        sut.setSpellcheckEnabled(false)
        val uiState = sut.uiState.value
        assertEquals(false, uiState.spellcheckEnabled)
        coVerify { mockKeyStore.save(KeyStoreKeys.SpellcheckEnabled, false) }
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationProviderThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        val index = 0
        sut.setMachineTranslationProvider(index)
        val uiState = sut.uiState.value
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS[index], uiState.currentProvider)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationProvider, index) }
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationKeyThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        val key = "key"
        sut.setMachineTranslationKey(key)
        val uiState = sut.uiState.value
        assertEquals(key, uiState.key)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationKey, key) }
    }

    @Test
    fun givenComponentCreatedWhenGenerateMachineTranslationKeyThenValuesAreRetrieved() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        coEvery { mockMachineTranslationRepository.generateKey(any(), any(), any()) } returns "new_key"
        lifecycle.create()

        sut.generateMachineTranslationKey("username", "password")
        val uiState = sut.uiState.value
        assertEquals("new_key", uiState.key)
        coVerify { mockMachineTranslationRepository.generateKey(username = "username", password = "password") }
    }

    @Test
    fun givenComponentCreatedWhenOpenLoginDialogThenDialogConfigIsChangedAccordingly() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        runOnUiThread {
            sut.openLoginDialog()
        }

        sut.dialog.configAsFlow<SettingsComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<SettingsComponent.DialogConfig.Login>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenCloseDialogThenDialogConfigIsChangedAccordingly() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        runOnUiThread {
            sut.closeDialog()
        }

        sut.dialog.configAsFlow<SettingsComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<SettingsComponent.DialogConfig.None>(item)
        }
    }
}
