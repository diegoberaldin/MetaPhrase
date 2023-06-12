package tmusecase.di

import org.koin.dsl.module
import tmusecase.ClearTmUseCase
import tmusecase.DefaultClearTmUseCase
import tmusecase.DefaultExportTmxUseCase
import tmusecase.DefaultGetSimilaritiesUseCase
import tmusecase.DefaultImportTmxUseCase
import tmusecase.DefaultSyncProjectWithTmUseCase
import tmusecase.ExportTmxUseCase
import tmusecase.GetSimilaritiesUseCase
import tmusecase.ImportTmxUseCase
import tmusecase.SyncProjectWithTmUseCase
import tmusecase.datasource.MemoryTranslationUnitSource
import tmusecase.datasource.ProjectTranslationUnitSource
import tmusecase.similarity.DefaultSimilarityCalculator
import tmusecase.similarity.SimilarityCalculator

val tmUseCaseModule = module {
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
    single<SyncProjectWithTmUseCase> {
        DefaultSyncProjectWithTmUseCase(
            dispatchers = get(),
            projectRepository = get(),
            segmentRepository = get(),
            languageRepository = get(),
            memoryEntryRepository = get(),
        )
    }
    single<GetSimilaritiesUseCase> {
        DefaultGetSimilaritiesUseCase(
            projectSource = get(),
            memorySource = get(),
        )
    }
    single<ExportTmxUseCase> {
        DefaultExportTmxUseCase(
            languageRepository = get(),
            segmentRepository = get(),
            dispatchers = get(),
        )
    }
}
