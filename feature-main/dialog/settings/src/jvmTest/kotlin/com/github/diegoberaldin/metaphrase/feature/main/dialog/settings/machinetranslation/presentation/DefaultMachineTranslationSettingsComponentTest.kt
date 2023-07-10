package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation

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
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class DefaultMachineTranslationSettingsComponentTest {
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
    private val mockKeyStore = mockk<TemporaryKeyStore>()
    private val mockMachineTranslationRepository = mockk<MachineTranslationRepository>()
    private val sut = DefaultMachineTranslationSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
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
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        val uiState = sut.uiState.value
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS.first(), uiState.currentProvider)
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS, uiState.availableProviders)
        assertEquals("key", uiState.key)
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationProviderThenValueIsUpdated() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        lifecycle.create()

        val index = 0
        sut.reduce(MachineTranslationSettingsComponent.Intent.SetMachineTranslationProvider(index))
        val uiState = sut.uiState.value
        assertEquals(MachineTranslationRepository.AVAILABLE_PROVIDERS[index], uiState.currentProvider)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationProvider, index) }
    }

    @Test
    fun givenComponentCreatedWhenSetMachineTranslationKeyThenValueIsUpdated() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        lifecycle.create()

        val key = "key"
        sut.reduce(MachineTranslationSettingsComponent.Intent.SetMachineTranslationKey(key))
        val uiState = sut.uiState.value
        assertEquals(key, uiState.key)
        coVerify { mockKeyStore.save(KeyStoreKeys.MachineTranslationKey, key) }
    }

    @Test
    fun givenComponentCreatedWhenGenerateMachineTranslationKeyThenValueIsUpdated() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        coEvery { mockKeyStore.save(any(), any<String>()) } returns Unit
        coEvery { mockMachineTranslationRepository.generateKey(any(), any(), any()) } returns "new_key"
        lifecycle.create()

        sut.reduce(MachineTranslationSettingsComponent.Intent.GenerateMachineTranslationKey("username", "password"))
        val uiState = sut.uiState.value
        assertEquals("new_key", uiState.key)
        coVerify { mockMachineTranslationRepository.generateKey(username = "username", password = "password") }
    }

    @Test
    fun givenComponentCreatedWhenOpenLoginDialogThenDialogConfigIsChangedAccordingly() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        runOnUiThread {
            sut.reduce(MachineTranslationSettingsComponent.Intent.OpenLoginDialog)
        }

        sut.dialog.configAsFlow<MachineTranslationSettingsComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<MachineTranslationSettingsComponent.DialogConfig.Login>(item)
        }
    }

    @Test
    fun givenComponentCreatedWhenCloseDialogThenDialogConfigIsChangedAccordingly() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationProvider, any<Int>()) } returns 0
        coEvery { mockKeyStore.get(KeyStoreKeys.MachineTranslationKey, any<String>()) } returns "key"
        lifecycle.create()

        runOnUiThread {
            sut.reduce(MachineTranslationSettingsComponent.Intent.CloseDialog)
        }

        sut.dialog.configAsFlow<MachineTranslationSettingsComponent.DialogConfig>().test {
            val item = awaitItem()
            assertIs<MachineTranslationSettingsComponent.DialogConfig.None>(item)
        }
    }
}
