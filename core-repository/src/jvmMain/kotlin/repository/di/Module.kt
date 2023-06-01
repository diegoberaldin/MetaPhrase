package repository.di

import org.koin.dsl.module
import repository.local.FlagsRepository
import repository.local.LanguageNameRepository
import repository.local.LanguageRepository
import repository.local.MemoryEntryRepository
import repository.local.ProjectRepository
import repository.local.SegmentRepository
import repository.usecase.ClearTmUseCase
import repository.usecase.ExportAndroidResourcesUseCase
import repository.usecase.ExportIosResourcesUseCase
import repository.usecase.ExportTmxUseCase
import repository.usecase.GetCompleteLanguageUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ImportTmxUseCase
import repository.usecase.ParseAndroidResourcesUseCase
import repository.usecase.ParseIosResourcesUseCase
import repository.usecase.ValidatePlaceholdersUseCase

val repositoryModule = module {
    single {
        ProjectRepository(
            dao = get(),
        )
    }
    single {
        LanguageRepository(
            dao = get(),
        )
    }
    single {
        LanguageNameRepository()
    }
    single {
        FlagsRepository()
    }
    single {
        SegmentRepository(
            dao = get(),
        )
    }
    single {
        MemoryEntryRepository(
            dao = get(),
        )
    }
}

val useCaseModule = module {
    single {
        GetCompleteLanguageUseCase(
            languageNameRepository = get(),
            flagsRepository = get(),
        )
    }
    single {
        ParseAndroidResourcesUseCase()
    }
    single {
        ParseIosResourcesUseCase()
    }
    single {
        ImportSegmentsUseCase(
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
    single {
        ExportAndroidResourcesUseCase()
    }
    single {
        ExportIosResourcesUseCase()
    }
    single {
        ValidatePlaceholdersUseCase()
    }
    single {
        ExportTmxUseCase(
            languageRepository = get(),
            segmentRepository = get(),
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
