package mainsettings.di

import mainsettings.ui.DefaultSettingsComponent
import mainsettings.ui.SettingsComponent
import org.koin.dsl.module

val mainSettingsModule = module {
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
