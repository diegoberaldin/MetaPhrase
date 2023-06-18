package com.github.diegoberaldin.metaphrase.domain.project.usecase.di

import com.github.diegoberaldin.metaphrase.domain.project.usecase.DefaultImportSegmentsUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.DefaultValidatePlaceholdersUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ImportSegmentsUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ValidatePlaceholdersUseCase
import org.koin.dsl.module

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
}
