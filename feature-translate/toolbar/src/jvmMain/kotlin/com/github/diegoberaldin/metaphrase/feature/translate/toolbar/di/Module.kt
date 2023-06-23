package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.di

import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.DefaultTranslateToolbarComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import org.koin.dsl.module

/**
 * DI module for the toolbar subproject.
 */
val translateToolbarModule = module {
    factory<TranslateToolbarComponent> { params ->
        DefaultTranslateToolbarComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            completeLanguage = get(),
        )
    }
}
