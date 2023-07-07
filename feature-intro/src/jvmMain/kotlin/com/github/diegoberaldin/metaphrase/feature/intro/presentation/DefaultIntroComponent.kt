package com.github.diegoberaldin.metaphrase.feature.intro.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

internal class DefaultIntroComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val mvi: MviModel<IntroComponent.Intent, IntroComponent.UiState, IntroComponent.Effect> = DefaultMviModel(
        IntroComponent.UiState,
    ),
) :
    IntroComponent,
    MviModel<IntroComponent.Intent, IntroComponent.UiState, IntroComponent.Effect> by mvi,
    ComponentContext by componentContext {
    private lateinit var viewModelScope: CoroutineScope

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }
}
