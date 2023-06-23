package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.di

import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.DefaultStatisticsComponent
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.StatisticsComponent
import org.koin.dsl.module

/**
 * DI module for statistics dialog.
 */
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
