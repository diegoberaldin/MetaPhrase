package dialogstatistics.di

import dialogstatistics.presentation.DefaultStatisticsComponent
import dialogstatistics.presentation.StatisticsComponent
import org.koin.dsl.module

val dialogStatisticsModule = module {
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
