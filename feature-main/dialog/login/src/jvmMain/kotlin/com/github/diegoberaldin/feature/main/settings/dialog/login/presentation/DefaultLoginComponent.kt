package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultLoginComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
) : LoginComponent, ComponentContext by componentContext {

    private lateinit var viewModelScope: CoroutineScope

    override val uiState = MutableStateFlow(LoginUiState())
    override val done = MutableSharedFlow<Pair<String, String>>()

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

    override fun setUsername(value: String) {
        uiState.update { it.copy(username = value) }
    }

    override fun setPassword(value: String) {
        uiState.update {
            it.copy(password = value)
        }
    }

    override fun submit() {
        uiState.update {
            it.copy(
                usernameError = "",
                passwordError = "",
            )
        }
        var valid = true
        val username = uiState.value.username
        val password = uiState.value.password
        if (username.isEmpty()) {
            uiState.update {
                it.copy(
                    usernameError = "message_missing_field".localized(),
                )
            }
            valid = false
        }
        if (password.isEmpty()) {
            uiState.update {
                it.copy(
                    passwordError = "message_missing_field".localized(),
                )
            }
            valid = false
        }
        if (!valid) {
            return
        }

        viewModelScope.launch(dispatchers.io) {
            done.emit(username to password)
        }
    }
}
