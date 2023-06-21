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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DefaultLoginComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
) : LoginComponent, ComponentContext by componentContext {

    private val username = MutableStateFlow("")
    private val usernameError = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val passwordError = MutableStateFlow("")
    private lateinit var viewModelScope: CoroutineScope

    override lateinit var uiState: StateFlow<LoginUiState>
    override val done = MutableSharedFlow<Pair<String, String>>()

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                uiState = combine(
                    username,
                    usernameError,
                    password,
                    passwordError,
                ) { username, usernameError, password, passwordError ->
                    LoginUiState(
                        username = username,
                        usernameError = usernameError,
                        password = password,
                        passwordError = passwordError,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = LoginUiState(),
                )
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    override fun setUsername(value: String) {
        username.value = value
    }

    override fun setPassword(value: String) {
        password.value = value
    }

    override fun submit() {
        usernameError.value = ""
        passwordError.value = ""
        var valid = true
        val username = username.value
        val password = password.value
        if (username.isEmpty()) {
            usernameError.value = "message_missing_field".localized()
            valid = false
        }
        if (password.isEmpty()) {
            passwordError.value = "message_missing_field".localized()
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
