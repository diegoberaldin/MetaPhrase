package formatspo.di

import formatspo.usecase.DefaultExportPoResourcesUseCase
import formatspo.usecase.DefaultParsePoUseCase
import formatspo.usecase.ExportPoUseCase
import formatspo.usecase.ParsePoUseCase
import org.koin.dsl.module

val poModule = module {
    single<ParsePoUseCase> {
        DefaultParsePoUseCase(
            dispatchers = get(),
        )
    }
    single<ExportPoUseCase> {
        DefaultExportPoResourcesUseCase(
            dispatchers = get(),
        )
    }
}
