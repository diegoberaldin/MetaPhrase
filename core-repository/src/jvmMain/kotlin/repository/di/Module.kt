package repository.di

import org.koin.dsl.module
import repository.local.FlagsRepository
import repository.local.LanguageNameRepository
import repository.local.LanguageRepository
import repository.local.ProjectRepository
import repository.local.SegmentRepository
import repository.usecase.*

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
}
