package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Login component.
 */
interface LoginComponent {
    /**
     * UI state.
     */
    val uiState: StateFlow<LoginUiState>

    /**
     * Submission events (first element: username, second element: password).
     */
    val done: SharedFlow<Pair<String, String>>

    /**
     * Set username.
     *
     * @param value username
     */
    fun setUsername(value: String)

    /**
     * Set password.
     *
     * @param value password
     */
    fun setPassword(value: String)

    /**
     * Submit the currently inserted values.
     */
    fun submit()
}
