package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultGeneralSettingsComponentTest {
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

        @JvmStatic
        @AfterAll
        fun teardown() {
            stopKoin()
        }
    }

    private val lifecycle = LifecycleRegistry()
    private val mockCompleteLanguage = mockk<GetCompleteLanguageUseCase>()
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val sut = DefaultGeneralSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        completeLanguage = mockCompleteLanguage,
        keyStore = mockKeyStore,
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
        lifecycle.create()

        val uiState = sut.uiState.value
        assertEquals("70", uiState.similarityThreshold)
        assertTrue(uiState.spellcheckEnabled)
        assertTrue(uiState.availableLanguages.isNotEmpty())
        assertNotNull(uiState.currentLanguage)
        assertEquals("en", uiState.currentLanguage?.code)
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
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        sut.reduce(GeneralSettingsComponent.Intent.SetLanguage(LanguageModel(code = "it")))
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
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        sut.reduce(GeneralSettingsComponent.Intent.SetSimilarity("80"))
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
        coEvery { mockKeyStore.save(any(), any<Boolean>()) } returns Unit
        lifecycle.create()

        sut.reduce(GeneralSettingsComponent.Intent.SetSpellcheckEnabled(false))
        val uiState = sut.uiState.value
        assertEquals(false, uiState.spellcheckEnabled)
        coVerify { mockKeyStore.save(KeyStoreKeys.SpellcheckEnabled, false) }
    }
}
