package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import kotlinx.coroutines.flow.StateFlow

/**
 * Representation of the available app themes to use as an observable state in the repository.
 */
sealed interface ThemeState {
    /**
     * Light theme
     */
    object Light : ThemeState

    /**
     * Dark theme
     */
    object Dark : ThemeState
}

/**
 * A single global repository for the application UI theme.
 */
interface ThemeRepository {
    /**
     * Observable current theme.
     */
    val theme: StateFlow<ThemeState>

    /**
     * Change the application UI theme.
     *
     * @param value Value to set
     */
    fun changeTheme(value: ThemeState)
}
