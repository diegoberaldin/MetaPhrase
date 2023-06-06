package repository.di

import org.koin.dsl.module
import repository.local.ProjectRepository
import repository.local.SegmentRepository
import repository.usecase.ExportIosResourcesUseCase
import repository.usecase.ExportTmxUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ParseIosResourcesUseCase
import repository.usecase.ValidatePlaceholdersUseCase

val repositoryModule = module {
    single {
        ProjectRepository(
            dao = get(),
        )
    }
    single {
        SegmentRepository(
            dao = get(),
        )
    }
}

val useCaseModule = module {
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
}
