package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.create
import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.core.common.di.commonModule
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.testutils.MockCoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.OpenSansFontFamily
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeState
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.TranslationThemeRepository
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.di.localizationModule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultAppearanceSettingsComponentTest {
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
    private val mockThemeRepository = mockk<ThemeRepository>()
    private val mockTranslationThemeRepository = mockk<TranslationThemeRepository>()
    private val sut = DefaultAppearanceSettingsComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        coroutineContext = TestScope().coroutineContext,
        dispatchers = MockCoroutineDispatcherProvider,
        keyStore = mockKeyStore,
        themeRepository = mockThemeRepository,
        translationThemeRepository = mockTranslationThemeRepository,
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
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.TranslationEditorFontSize, any<Int>()) } returns 14
        lifecycle.create()

        val uiState = sut.uiState.value
        assertTrue(uiState.darkModeEnabled)
    }

    @Test
    fun givenComponentCreatedWhenSetDarkModeDisabledThenValueIsUpdated() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.TranslationEditorFontSize, any<Int>()) } returns 14
        coEvery { mockKeyStore.save(any(), any<Boolean>()) } returns Unit
        coEvery { mockThemeRepository.changeTheme(any()) } returns Unit
        lifecycle.create()

        sut.reduce(AppearanceSettingsComponent.Intent.SetDarkMode(false))
        val uiState = sut.uiState.value
        assertEquals(false, uiState.darkModeEnabled)
        coVerify { mockKeyStore.save(KeyStoreKeys.DarkThemeEnabled, false) }
        coVerify { mockThemeRepository.changeTheme(ThemeState.Light) }
    }

    @Test
    fun givenComponentCreatedWhenSetEditorFontSizeThenValueIsUpdated() = runTest {
        coEvery { mockKeyStore.get(KeyStoreKeys.DarkThemeEnabled, any<Boolean>()) } returns true
        coEvery { mockKeyStore.get(KeyStoreKeys.TranslationEditorFontSize, any<Int>()) } returns 14
        coEvery { mockKeyStore.save(any(), any<Int>()) } returns Unit
        coEvery { mockTranslationThemeRepository.textStyle } returns MutableStateFlow(
            TextStyle(
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = (0.4).sp,
            ),
        )
        coEvery { mockTranslationThemeRepository.changeTextStyle(any()) } returns Unit
        lifecycle.create()

        sut.reduce(AppearanceSettingsComponent.Intent.SetEditorFontSize(12))
        val uiState = sut.uiState.value
        assertEquals("12", uiState.editorFontSize)
        coVerify { mockKeyStore.save(KeyStoreKeys.TranslationEditorFontSize, 12) }
        coVerify { mockTranslationThemeRepository.changeTextStyle(withArg { assertEquals(12.sp, it.fontSize) }) }
    }
}
