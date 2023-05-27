package translationtranslationmemory.di

import org.koin.dsl.module
import translationmemory.di.translationMemoryModule

val translateTranslationMemoryModule = module {
    includes(translationMemoryModule)
}
