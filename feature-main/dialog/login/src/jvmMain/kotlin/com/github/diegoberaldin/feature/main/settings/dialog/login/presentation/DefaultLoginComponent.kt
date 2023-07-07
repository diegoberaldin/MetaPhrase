package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.architecture.DefaultMviModel
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultLoginComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val mvi: DefaultMviModel<LoginComponent.Intent, LoginComponent.UiState, LoginComponent.Effect> = DefaultMviModel(
        LoginComponent.UiState(),
    ),
) : LoginComponent,
    MviModel<LoginComponent.Intent, LoginComponent.UiState, LoginComponent.Effect> by mvi,
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

    override fun reduce(intent: LoginComponent.Intent) {
        when (intent) {
            is LoginComponent.Intent.SetUsername -> mvi.updateState { it.copy(username = intent.value) }
            is LoginComponent.Intent.SetPassword -> mvi.updateState { it.copy(password = intent.value) }
            LoginComponent.Intent.Submit -> submit()
        }
    }

    private fun submit() {
        mvi.updateState {
            it.copy(
                usernameError = "",
                passwordError = "",
            )
        }
        var valid = true
        val username = uiState.value.username
        val password = uiState.value.password
        if (username.isEmpty()) {
            mvi.updateState {
                it.copy(
                    usernameError = "message_missing_field".localized(),
                )
            }
            valid = false
        }
        if (password.isEmpty()) {
            mvi.updateState {
                it.copy(
                    passwordError = "message_missing_field".localized(),
                )
            }
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.main) {
            mvi.emitEffect(LoginComponent.Effect.Done(username, password))
        }
    }
}
