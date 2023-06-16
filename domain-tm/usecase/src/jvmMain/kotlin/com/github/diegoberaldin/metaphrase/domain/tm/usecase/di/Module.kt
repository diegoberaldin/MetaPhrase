package com.github.diegoberaldin.metaphrase.domain.tm.usecase.di

import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ClearTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.DefaultClearTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.DefaultExportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.DefaultGetSimilaritiesUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.DefaultImportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.DefaultSyncProjectWithTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ExportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.GetSimilaritiesUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ImportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.SyncProjectWithTmUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.MemoryTranslationUnitSource
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.ProjectTranslationUnitSource
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.similarity.DefaultSimilarityCalculator
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.similarity.SimilarityCalculator
import org.koin.dsl.module

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
