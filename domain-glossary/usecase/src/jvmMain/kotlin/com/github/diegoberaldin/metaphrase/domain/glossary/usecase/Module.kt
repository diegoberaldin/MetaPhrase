package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import org.koin.dsl.module

val glossaryUseCaseModule = module {
    single<GetGlossaryTermsUseCase> {
        DefaultGetGlossaryTermsUseCase(
            repository = get(),
            dispatchers = get(),
            spelling = get(),
        )
    }
    single<ExportGlossaryUseCase> {
        DefaultExportGlossaryUseCase(
            repository = get(),
            dispatchers = get(),
        )
    }
}
