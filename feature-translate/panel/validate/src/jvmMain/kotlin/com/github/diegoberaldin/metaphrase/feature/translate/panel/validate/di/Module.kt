package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.di

import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation.DefaultValidateComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation.ValidateComponent
import org.koin.dsl.module

/**
 * DI module for the validate panel.
 */
val panelValidateModule = module {
    factory<ValidateComponent> { params ->
        DefaultValidateComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
}
