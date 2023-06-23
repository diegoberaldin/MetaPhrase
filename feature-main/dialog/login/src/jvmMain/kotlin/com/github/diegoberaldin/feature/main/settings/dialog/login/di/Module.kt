package com.github.diegoberaldin.feature.main.settings.dialog.login.di

import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.DefaultLoginComponent
import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.LoginComponent
import org.koin.dsl.module

/**
 * DI module for the login dialog.
 */
val dialogLoginModule = module {
    factory<LoginComponent> {
        DefaultLoginComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
        )
    }
}
