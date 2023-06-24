package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.di

import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation.DefaultNewSegmentComponent
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation.NewSegmentComponent
import org.koin.dsl.module

/**
 * DI module for new segment dialog.
 */
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
