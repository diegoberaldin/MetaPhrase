package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.di

import com.github.diegoberaldin.metaphrase.domain.glossary.di.glossaryModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation.DefaultGlossaryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation.GlossaryComponent
import org.koin.dsl.module

/**
 * DI module for the glossary panel.
 */
val panelGlossaryModule = module {
    includes(glossaryModule)

    factory<GlossaryComponent> {
        DefaultGlossaryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            languageRepository = get(),
            flagsRepository = get(),
            segmentRepository = get(),
            glossaryTermRepository = get(),
            getGlossaryTerms = get(),
        )
    }
}
