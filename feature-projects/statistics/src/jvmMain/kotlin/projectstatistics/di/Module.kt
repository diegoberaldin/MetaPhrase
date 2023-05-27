package projectstatistics.di

import org.koin.dsl.module
import projectstatistics.ui.DefaultStatisticsComponent
import projectstatistics.ui.StatisticsComponent

val projectStatisticsModule = module {
    factory<StatisticsComponent> { params ->
        DefaultStatisticsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
            completeLanguage = get(),
        )
    }
}
