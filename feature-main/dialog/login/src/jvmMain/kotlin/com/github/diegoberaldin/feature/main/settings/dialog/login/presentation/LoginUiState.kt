package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

data class LoginUiState(
    val username: String = "",
    val usernameError: String = "",
    val password: String = "",
    val passwordError: String = "",
)
