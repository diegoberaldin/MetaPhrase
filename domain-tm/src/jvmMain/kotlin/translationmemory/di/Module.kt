package translationmemory.di

import org.koin.dsl.module
import translationmemory.datasource.MemoryTranslationUnitSource
import translationmemory.datasource.ProjectTranslationUnitSource
import translationmemory.repo.DefaultTranslationMemoryRepository
import translationmemory.repo.MemoryEntryRepository
import translationmemory.repo.TranslationMemoryRepository
import translationmemory.similarity.DefaultSimilarityCalculator
import translationmemory.similarity.SimilarityCalculator
import translationmemory.usecase.ClearTmUseCase
import translationmemory.usecase.ImportTmxUseCase

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
    single {
        MemoryEntryRepository(
            dao = get(),
        )
    }
    single {
        ImportTmxUseCase(
            memoryEntryRepository = get(),
        )
    }
    single {
        ClearTmUseCase(
            memoryEntryRepository = get(),
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
