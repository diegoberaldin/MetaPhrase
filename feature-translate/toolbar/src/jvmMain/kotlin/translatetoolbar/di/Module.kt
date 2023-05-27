package translatetoolbar.di

import org.koin.dsl.module
import translatetoolbar.ui.DefaultTranslateToolbarComponent
import translatetoolbar.ui.TranslateToolbarComponent

val translateToolbarModule = module {
    factory<TranslateToolbarComponent> { params ->
        DefaultTranslateToolbarComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            completeLanguage = get(),
            logManager = get(),
        )
    }
}
