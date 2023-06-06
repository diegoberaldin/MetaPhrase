package repository.di

import org.koin.dsl.module
import repository.repo.DefaultProjectRepository
import repository.repo.DefaultSegmentRepository
import repository.repo.ProjectRepository
import repository.repo.SegmentRepository
import repository.usecase.DefaultExportTmxUseCase
import repository.usecase.DefaultImportSegmentsUseCase
import repository.usecase.DefaultValidatePlaceholdersUseCase
import repository.usecase.ExportTmxUseCase
import repository.usecase.ImportSegmentsUseCase
import repository.usecase.ValidatePlaceholdersUseCase

val projectModule = module {
    single<ProjectRepository> {
        DefaultProjectRepository(
            dao = get(),
        )
    }
    single<SegmentRepository> {
        DefaultSegmentRepository(
            dao = get(),
        )
    }
    single<ImportSegmentsUseCase> {
        DefaultImportSegmentsUseCase(
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
    single<ValidatePlaceholdersUseCase> {
        DefaultValidatePlaceholdersUseCase()
    }
    single<ExportTmxUseCase> {
        DefaultExportTmxUseCase(
            languageRepository = get(),
            segmentRepository = get(),
        )
    }
}
