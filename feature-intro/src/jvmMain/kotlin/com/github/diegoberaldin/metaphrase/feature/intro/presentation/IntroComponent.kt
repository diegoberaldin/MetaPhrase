package com.github.diegoberaldin.metaphrase.feature.intro.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * Intro component contract.
 */
interface IntroComponent : MviModel<IntroComponent.ViewIntent, IntroComponent.UiState, IntroComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface ViewIntent

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * UI state.
     */
    object UiState
}
