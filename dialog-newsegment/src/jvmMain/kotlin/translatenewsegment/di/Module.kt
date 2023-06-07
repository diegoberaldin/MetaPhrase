package translatenewsegment.di

import org.koin.dsl.module
import translatenewsegment.ui.DefaultNewSegmentComponent
import translatenewsegment.ui.NewSegmentComponent

val translateNewSegmentModule = module {
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
