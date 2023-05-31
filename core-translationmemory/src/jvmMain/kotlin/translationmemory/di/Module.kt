package translationmemory.di

import org.koin.dsl.module
import translationmemory.repo.DefaultSimilarityCalculator
import translationmemory.repo.DefaultTranslationMemoryRepository
import translationmemory.repo.MemoryTranslationUnitSource
import translationmemory.repo.ProjectTranslationUnitSource
import translationmemory.repo.SimilarityCalculator
import translationmemory.repo.TranslationMemoryRepository

internal val innerTranslationMemoryModule = module {
    single<SimilarityCalculator> {
        DefaultSimilarityCalculator()
    }
    single {
        ProjectTranslationUnitSource(
            languageRepository = get(),
            segmentRepository = get(),
            calculateSimilarity = get(),
        )
    }
    single {
        MemoryTranslationUnitSource(
            languageRepository = get(),
            segmentRepository = get(),
            memoryEntryRepository = get(),
            calculateSimilarity = get(),
        )
    }
}

val translationMemoryModule = module {
    includes(innerTranslationMemoryModule)

    single<TranslationMemoryRepository> {
        DefaultTranslationMemoryRepository(
            projectSource = get(),
            memorySource = get(),
        )
    }
}
