package dialogsettings.di

import dialogsettings.ui.DefaultSettingsComponent
import dialogsettings.ui.SettingsComponent
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
