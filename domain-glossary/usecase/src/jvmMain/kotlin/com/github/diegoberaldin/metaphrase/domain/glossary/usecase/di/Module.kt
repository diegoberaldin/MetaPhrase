package com.github.diegoberaldin.metaphrase.domain.glossary.usecase.di

import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ClearGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.DefaultClearGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.DefaultExportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.DefaultGetGlossaryTermsUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.DefaultImportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ExportGlossaryUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.GetGlossaryTermsUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.ImportGlossaryUseCase
import org.koin.dsl.module

/**
 * DI module for the domain-glossary use case subproject.
 */
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
    single<ImportGlossaryUseCase> {
        DefaultImportGlossaryUseCase(
            repository = get(),
            dispatchers = get(),
        )
    }
    single<ClearGlossaryUseCase> {
        DefaultClearGlossaryUseCase(
            repository = get(),
        )
    }
}
