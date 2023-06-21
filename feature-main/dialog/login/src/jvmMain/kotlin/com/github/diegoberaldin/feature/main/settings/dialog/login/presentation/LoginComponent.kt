package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {
    val uiState: StateFlow<LoginUiState>
    val done: SharedFlow<Pair<String, String>>

    fun setUsername(value: String)
    fun setPassword(value: String)
    fun submit()
}
