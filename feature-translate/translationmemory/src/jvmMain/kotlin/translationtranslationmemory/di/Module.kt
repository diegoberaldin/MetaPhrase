package translationtranslationmemory.di

import org.koin.dsl.module
import translationmemory.di.translationMemoryModule
import translationtranslationmemory.ui.DefaultTranslationMemoryComponent
import translationtranslationmemory.ui.TranslationMemoryComponent

val translateTranslationMemoryModule = module {
    includes(translationMemoryModule)

    factory<TranslationMemoryComponent> {
        DefaultTranslationMemoryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            segmentRepository = get(),
            translationMemoryRepository = get(),
        )
    }
}
