package newsegment.di

import newsegment.presentation.DefaultNewSegmentComponent
import newsegment.presentation.NewSegmentComponent
import org.koin.dsl.module

val dialogNewSegmentModule = module {
    factory<NewSegmentComponent> { params ->
        DefaultNewSegmentComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
}
