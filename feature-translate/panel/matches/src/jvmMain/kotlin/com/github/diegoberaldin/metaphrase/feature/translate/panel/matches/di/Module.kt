package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.di

import com.github.diegoberaldin.metaphrase.domain.tm.di.translationMemoryModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation.DefaultTranslationMemoryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation.TranslationMemoryComponent
import org.koin.dsl.module

/**
 * DI module for the TM matches panel section
 */
val panelMatchesModule = module {
    includes(translationMemoryModule)

    factory<TranslationMemoryComponent> {
        DefaultTranslationMemoryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            segmentRepository = get(),
            getSimilarities = get(),
            keyStore = get(),
        )
    }
}
