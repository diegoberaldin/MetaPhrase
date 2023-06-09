package panelvalidate.di

import org.koin.dsl.module
import panelvalidate.ui.DefaultValidateComponent
import panelvalidate.ui.ValidateComponent

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
