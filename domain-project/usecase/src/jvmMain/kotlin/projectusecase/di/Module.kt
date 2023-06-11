package projectusecase.di

import org.koin.dsl.module
import projectusecase.DefaultGetCompleteLanguageUseCase
import projectusecase.DefaultImportSegmentsUseCase
import projectusecase.DefaultValidatePlaceholdersUseCase
import projectusecase.GetCompleteLanguageUseCase
import projectusecase.ImportSegmentsUseCase
import projectusecase.ValidatePlaceholdersUseCase

val projectUseCaseModule = module {
    single<ImportSegmentsUseCase> {
        DefaultImportSegmentsUseCase(
            languageRepository = get(),
            segmentRepository = get(),
            dispatchers = get(),
        )
    }
    single<ValidatePlaceholdersUseCase> {
        DefaultValidatePlaceholdersUseCase(
            dispatchers = get(),
        )
    }
    single<GetCompleteLanguageUseCase> {
        DefaultGetCompleteLanguageUseCase(
            languageNameRepository = get(),
            flagsRepository = get(),
        )
    }
}
