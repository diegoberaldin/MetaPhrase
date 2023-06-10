package panelmatches.di

import org.koin.dsl.module
import panelmatches.presentation.DefaultTranslationMemoryComponent
import panelmatches.presentation.TranslationMemoryComponent
import translationmemory.di.translationMemoryModule

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
