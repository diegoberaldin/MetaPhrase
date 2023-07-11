package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation

import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.ThemeState
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.TranslationThemeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DefaultAppearanceSettingsComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<AppearanceSettingsComponent.Intent, AppearanceSettingsComponent.UiState, AppearanceSettingsComponent.Effect> = DefaultMviModel(
        AppearanceSettingsComponent.UiState(),
    ),
    private val keyStore: TemporaryKeyStore,
    private val themeRepository: ThemeRepository,
    private val translationThemeRepository: TranslationThemeRepository,
) : AppearanceSettingsComponent,
    MviModel<AppearanceSettingsComponent.Intent, AppearanceSettingsComponent.UiState, AppearanceSettingsComponent.Effect> by mvi,
    ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                viewModelScope.launch(dispatchers.io) {
                    mvi.updateState { it.copy(isLoading = true) }
                    val darkModeEnabled = keyStore.get(KeyStoreKeys.DarkThemeEnabled, false)
                    val editorFontSize = keyStore.get(KeyStoreKeys.TranslationEditorFontSize, 14).toString()
                    val fontIndex = keyStore.get(KeyStoreKeys.TranslationEditorFontType, 0)
                    val availableFonts = translationThemeRepository.getAvailableFontFamilies()
                    val fontFamily = availableFonts.getOrNull(fontIndex) ?: availableFonts.first()
                    val familyNames = List(availableFonts.size) { idx ->
                        translationThemeRepository.getFamilyName(idx)
                    }
                    mvi.updateState {
                        it.copy(
                            isLoading = false,
                            darkModeEnabled = darkModeEnabled,
                            editorFontSize = editorFontSize,
                            availableFontTypes = familyNames,
                            editorFontType = familyNames[fontIndex],
                        )
                    }
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun reduce(intent: AppearanceSettingsComponent.Intent) {
        when (intent) {
            is AppearanceSettingsComponent.Intent.SetDarkMode -> setDarkMode(intent.value)
            is AppearanceSettingsComponent.Intent.SetEditorFontSize -> setEditorFontSize(intent.value)
            is AppearanceSettingsComponent.Intent.SetEditorFontType -> setEditorFontType(intent.index)
        }
    }

    private fun setDarkMode(enabled: Boolean) {
        if (enabled) {
            themeRepository.changeTheme(ThemeState.Dark)
        } else {
            themeRepository.changeTheme(ThemeState.Light)
        }
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.DarkThemeEnabled, enabled)
        }
        mvi.updateState { it.copy(darkModeEnabled = enabled) }
    }

    private fun setEditorFontSize(value: Int) {
        val newStyle = translationThemeRepository.textStyle.value.copy(
            fontSize = value.sp,
        )
        translationThemeRepository.changeTextStyle(newStyle)
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.TranslationEditorFontSize, value)
        }
        mvi.updateState { it.copy(editorFontSize = value.toString()) }
    }

    private fun setEditorFontType(index: Int) {
        val availableFonts = translationThemeRepository.getAvailableFontFamilies()
        val fontFamily = availableFonts.getOrNull(index) ?: availableFonts.first()
        val newStyle = translationThemeRepository.textStyle.value.copy(
            fontFamily = fontFamily,
        )
        translationThemeRepository.changeTextStyle(newStyle)
        viewModelScope.launch(dispatchers.io) {
            keyStore.save(KeyStoreKeys.TranslationEditorFontType, index)
        }
        val fontName = translationThemeRepository.getFamilyName(index)
        mvi.updateState { it.copy(editorFontType = fontName) }
    }
}
