package translationmemory.di

import org.koin.dsl.module
import translationmemory.datasource.MemoryTranslationUnitSource
import translationmemory.datasource.ProjectTranslationUnitSource
import translationmemory.repo.DefaultMemoryEntryRepository
import translationmemory.repo.MemoryEntryRepository
import translationmemory.similarity.DefaultSimilarityCalculator
import translationmemory.similarity.SimilarityCalculator
import translationmemory.usecase.ClearTmUseCase
import translationmemory.usecase.DefaultClearTmUseCase
import translationmemory.usecase.DefaultGetSimilaritiesUseCase
import translationmemory.usecase.DefaultImportTmxUseCase
import translationmemory.usecase.GetSimilaritiesUseCase
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
    single<MemoryEntryRepository> {
        DefaultMemoryEntryRepository(
            dao = get(),
        )
    }
    single<ImportTmxUseCase> {
        DefaultImportTmxUseCase(
            memoryEntryRepository = get(),
            dispatchers = get(),
        )
    }
    single<ClearTmUseCase> {
        DefaultClearTmUseCase(
            memoryEntryRepository = get(),
        )
    }
}

val translationMemoryModule = module {
    includes(innerTranslationMemoryModule)

    single<GetSimilaritiesUseCase> {
        DefaultGetSimilaritiesUseCase(
            projectSource = get(),
            memorySource = get(),
        )
    }
}
