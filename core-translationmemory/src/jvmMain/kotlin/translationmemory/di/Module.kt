package translationmemory.di

import org.koin.dsl.module
import translationmemory.repo.DefaultTranslationMemoryRepository
import translationmemory.repo.TranslationMemoryRepository

val translationMemoryModule = module {
    single<TranslationMemoryRepository> {
        DefaultTranslationMemoryRepository(
            segmentRepository = get(),
            languageRepository = get(),
        )
    }
}
