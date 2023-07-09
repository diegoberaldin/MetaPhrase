package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di

import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation.DefaultSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation.SettingsComponent
import org.koin.dsl.module

val dialogSettingsModule = module {
    includes(dialogLoginModule)

    factory<SettingsComponent> { params ->
        DefaultSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            completeLanguage = get(),
            machineTranslationRepository = get(),
            keyStore = get(),
            themeRepository = get(),
            translationThemeRepository = get(),
        )
    }
}
