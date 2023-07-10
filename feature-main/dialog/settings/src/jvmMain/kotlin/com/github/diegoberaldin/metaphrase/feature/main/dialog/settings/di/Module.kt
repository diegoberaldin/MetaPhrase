package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di

import com.github.diegoberaldin.feature.main.settings.dialog.login.di.dialogLoginModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation.AppearanceSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation.DefaultAppearanceSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation.DefaultGeneralSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation.GeneralSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation.DefaultMachineTranslationSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation.MachineTranslationSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation.DefaultSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation.SettingsComponent
import org.koin.dsl.module

val generalSettingsModule = module {
    factory<GeneralSettingsComponent> { params ->
        DefaultGeneralSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            completeLanguage = get(),
            keyStore = get(),
        )
    }
}

val appearanceSettingsModule = module {
    factory<AppearanceSettingsComponent> { params ->
        DefaultAppearanceSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            keyStore = get(),
            themeRepository = get(),
            translationThemeRepository = get(),
        )
    }
}

val machineTranslationSettingsModule = module {
    factory<MachineTranslationSettingsComponent> { params ->
        DefaultMachineTranslationSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            keyStore = get(),
            machineTranslationRepository = get(),
        )
    }
}

val dialogSettingsModule = module {
    includes(
        dialogLoginModule,
        generalSettingsModule,
        appearanceSettingsModule,
        machineTranslationSettingsModule,
    )

    factory<SettingsComponent> { params ->
        DefaultSettingsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
        )
    }
}
