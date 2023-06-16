package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di

import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.ui.DefaultSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.ui.SettingsComponent
import org.koin.dsl.module

val dialogSettingsModule = module {
    factory<SettingsComponent> { params ->
        DefaultSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            completeLanguage = get(),
            keyStore = get(),
        )
    }
}
