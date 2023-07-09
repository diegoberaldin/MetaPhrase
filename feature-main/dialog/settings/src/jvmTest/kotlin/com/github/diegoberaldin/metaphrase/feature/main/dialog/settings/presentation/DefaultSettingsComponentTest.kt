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
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeState
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
    private val mockThemeRepository = mockk<ThemeRepository>()
    private val sut = DefaultSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        completeLanguage = mockCompleteLanguage,
        keyStore = mockKeyStore,
        machineTranslationRepository = mockMachineTranslationRepository,
        themeRepository = mockThemeRepository,
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
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
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
        assertTrue(uiState.darkModeEnabled)
    }

    @Test
    fun givenComponentCreatedWhenSetLanguageThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        sut.reduce(SettingsComponent.Intent.SetLanguage(LanguageModel(code = "it")))
        val uiState = sut.uiState.value
        assertEquals("it", uiState.currentLanguage?.code)
        coVerify { mockKeyStore.save(KeyStoreKeys.AppLanguage, "it") }
    }

    @Test
    fun givenComponentCreatedWhenSetSimilarityThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        sut.reduce(SettingsComponent.Intent.SetSimilarity("80"))
        val uiState = sut.uiState.value
        assertEquals("80", uiState.similarityThreshold)
        coVerify { mockKeyStore.save(KeyStoreKeys.SimilarityThreshold, 80) }
    }

    @Test
    fun givenComponentCreatedWhenSetSpellcheckEnabledThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<Boolean>()) } returns Unit
        lifecycle.create()

        sut.reduce(SettingsComponent.Intent.SetSpellcheckEnabled(false))
        val uiState = sut.uiState.value
        assertEquals(false, uiState.spellcheckEnabled)
        coVerify { mockKeyStore.save(KeyStoreKeys.SpellcheckEnabled, false) }
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationProviderThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        val index = 0
        sut.reduce(SettingsComponent.Intent.SetMachineTranslationProvider(index))
        val uiState = sut.uiState.value
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS[index], uiState.currentProvider)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationProvider, index) }
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationKeyThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        val key = "key"
        sut.reduce(SettingsComponent.Intent.SetMachineTranslationKey(key))
        val uiState = sut.uiState.value
        assertEquals(key, uiState.key)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationKey, key) }
    }

    @Test
    fun givenComponentCreatedWhenGenerateMachineTranslationKeyThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        coEvery { mockMachineTranslationRepository.generateKey(any(), any(), any()) } returns "new_key"
        lifecycle.create()

        sut.reduce(SettingsComponent.Intent.GenerateMachineTranslationKey("username", "password"))
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
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        lifecycle.create()

        runOnUiThread {
            sut.reduce(SettingsComponent.Intent.OpenLoginDialog)
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
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        lifecycle.create()

        runOnUiThread {
            sut.reduce(SettingsComponent.Intent.CloseDialog)
        }

        sut.dialog.configAsFlow<SettingsComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<SettingsComponent.DialogConfig.None>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenSetDarkModeDisabledThenValueIsUpdated() = runTest {
        val languageSlot = slot<LanguageModel>()
        every { mockCompleteLanguage.invoke(capture(languageSlot)) } answers {
            val original = languageSlot.captured
            original.copy(name = original.code)
        }
        coEvery { mockKeyStore.get(KeyStoreKeys.SpellcheckEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.SimilarityThreshold, any<Int>()) } returns 70
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.save(any(), any<Boolean>()) } returns Unit
        coEvery { mockThemeRepository.changeTheme(any()) } returns Unit
        lifecycle.create()

        sut.reduce(SettingsComponent.Intent.SetDarkMode(false))
        val uiState = sut.uiState.value
        assertEquals(false, uiState.darkModeEnabled)
        coVerify { mockKeyStore.save(KeyStoreKeys.DarkThemeEnabled, false) }
        coVerify { mockThemeRepository.changeTheme(ThemeState.Light) }
    }
}
