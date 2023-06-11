package panelglossary.di

import glossary.di.glossaryModule
import org.koin.dsl.module
import panelglossary.presentation.DefaultGlossaryComponent
import panelglossary.presentation.GlossaryComponent

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
