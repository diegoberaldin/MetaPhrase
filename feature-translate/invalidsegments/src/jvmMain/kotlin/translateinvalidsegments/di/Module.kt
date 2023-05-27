package translateinvalidsegments.di

import org.koin.dsl.module
import translateinvalidsegments.ui.DefaultInvalidSegmentComponent
import translateinvalidsegments.ui.InvalidSegmentComponent

val translateInvalidSegmentsModule = module {
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
