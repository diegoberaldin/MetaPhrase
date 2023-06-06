package ios.di

import ios.usecase.DefaultExportIosResourcesUseCase
import ios.usecase.DefaultParseIosResourcesUseCase
import ios.usecase.ExportIosResourcesUseCase
import ios.usecase.ParseIosResourcesUseCase
import org.koin.dsl.module

val iosModule = module {
    single<ParseIosResourcesUseCase> {
        DefaultParseIosResourcesUseCase()
    }
    single<ExportIosResourcesUseCase> {
        DefaultExportIosResourcesUseCase()
    }
}
