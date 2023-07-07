package com.github.diegoberaldin.feature.main.settings.dialog.login.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

/**
 * Login component contract.
 */
interface LoginComponent : MviModel<LoginComponent.Intent, LoginComponent.UiState, LoginComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Set username.
         *
         * @property value username value
         * @constructor Create [SetUsername]
         */
        data class SetUsername(val value: String) : Intent

        /**
         * Set password.
         *
         * @property value password value
         * @constructor Create [SetPassword]
         */
        data class SetPassword(val value: String) : Intent

        /**
         * Submit intent.
         */
        object Submit : Intent
    }

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Done.
         *
         * @property username username value
         * @property password password value
         * @constructor Create [Done]
         */
        data class Done(val username: String, val password: String) : Effect
    }

    /**
     * Login UI state.
     *
     * @property username user email
     * @property usernameError error message for the username field
     * @property password user password
     * @property passwordError error message for the password field
     * @constructor Create [LoginUiState]
     */
    data class UiState(
        val username: String = "",
        val usernameError: String = "",
        val password: String = "",
        val passwordError: String = "",
    )
}
