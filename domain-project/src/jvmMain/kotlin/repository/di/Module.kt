package repository.di

import org.koin.dsl.module
import repository.repo.ProjectRepository
import repository.repo.SegmentRepository
import repository.usecase.ExportTmxUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ValidatePlaceholdersUseCase

val projectModule = module {
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
    single {
        ImportSegmentsUseCase(
            languageRepository = get(),
            segmentRepository = get(),
        )
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
