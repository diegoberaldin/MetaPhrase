package panelmatches.di

import org.koin.dsl.module
import translationmemory.di.translationMemoryModule
import panelmatches.ui.DefaultTranslationMemoryComponent
import panelmatches.ui.TranslationMemoryComponent

val panelMatchesModule = module {
    includes(translationMemoryModule)

    factory<TranslationMemoryComponent> {
        DefaultTranslationMemoryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            segmentRepository = get(),
            translationMemoryRepository = get(),
            keyStore = get(),
        )
    }
}
