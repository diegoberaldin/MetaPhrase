package panelvalidate.di

import org.koin.dsl.module
import panelvalidate.ui.DefaultInvalidSegmentComponent
import panelvalidate.ui.InvalidSegmentComponent

val panelValidateModule = module {
    factory<InvalidSegmentComponent> { params ->
        DefaultInvalidSegmentComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
}
