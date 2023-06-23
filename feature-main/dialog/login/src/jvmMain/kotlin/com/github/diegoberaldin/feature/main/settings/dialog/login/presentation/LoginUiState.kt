package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

/**
 * Login ui state.
 *
 * @property username user email
 * @property usernameError error message for the username field
 * @property password user password
 * @property passwordError error message for the password field
 * @constructor Create [LoginUiState]
 */
data class LoginUiState(
    val username: String = "",
    val usernameError: String = "",
    val password: String = "",
    val passwordError: String = "",
)
