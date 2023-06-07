package panelglossary.di

import glossary.di.glossaryModule
import org.koin.dsl.module
import panelglossary.ui.DefaultGlossaryComponent
import panelglossary.ui.GlossaryComponent

val panelGlossaryModule = module {
    includes(glossaryModule)

    factory<GlossaryComponent> {
        DefaultGlossaryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
            glossaryTermRepository = get(),
            getGlossaryTerms = get(),
        )
    }
}
