package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * Settings component contract.
 */
interface SettingsComponent :
    MviModel<SettingsComponent.Intent, SettingsComponent.UiState, SettingsComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        data class ChangeTab(val index: Int) : Intent
    }

    /**
     * UI state for general settings.
     *
     * @property isLoading true if there is a background operation in progress
     * @constructor Create [UiState]
     */
    data class UiState(
        val isLoading: Boolean = false,
        val currentTab: Int = 0,
    )

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * Content navigation slot value.
     */
    val config: Value<ChildSlot<ContentConfig, *>>

    /**
     * Available content configurations.
     */
    sealed interface ContentConfig : Parcelable {
        /**
         * General settings.
         */
        @Parcelize
        object General : ContentConfig

        /**
         * Look and feel settings.
         */
        @Parcelize
        object Appearance : ContentConfig

        /**
         * Machine translation settings.
         */
        @Parcelize
        object MachineTranslation : ContentConfig
    }
}
