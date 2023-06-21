package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {
    val uiState: StateFlow<SettingsUiState>
    val languageUiState: StateFlow<SettingsLanguageUiState>
    val machineTranslationUiState: StateFlow<SettingsMachineTranslationUiState>
    val dialog: Value<ChildSlot<DialogConfig, *>>

    fun setLanguage(value: LanguageModel)
    fun setSimilarity(value: String)
    fun setSpellcheckEnabled(value: Boolean)
    fun setMachineTranslationProvider(index: Int)
    fun setMachineTranslationKey(value: String)
    fun openLoginDialog()
    fun closeDialog()
    fun generateMachineTranslationKey(username: String, password: String)

    sealed interface DialogConfig : Parcelable {
        @Parcelize
        object None : DialogConfig

        @Parcelize
        object Login : DialogConfig
    }
}
