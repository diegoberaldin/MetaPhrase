package formatsios.di

import formatsios.usecase.DefaultExportIosResourcesUseCase
import formatsios.usecase.DefaultParseIosResourcesUseCase
import formatsios.usecase.ExportIosResourcesUseCase
import formatsios.usecase.ParseIosResourcesUseCase
import org.koin.dsl.module

val iosModule = module {
    single<ParseIosResourcesUseCase> {
        DefaultParseIosResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportIosResourcesUseCase> {
        DefaultExportIosResourcesUseCase(
            dispatchers = get(),
        )
    }
}
