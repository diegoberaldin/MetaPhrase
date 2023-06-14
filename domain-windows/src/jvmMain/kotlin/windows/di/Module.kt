package windows.di

import org.koin.dsl.module
import windows.usecase.DefaultExportWindowsResourcesUseCase
import windows.usecase.DefaultParseWindowsResourcesUseCase
import windows.usecase.ExportWindowsResourcesUseCase
import windows.usecase.ParseWindowsResourcesUseCase

val windowsModule = module {
    single<ParseWindowsResourcesUseCase> {
        DefaultParseWindowsResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportWindowsResourcesUseCase> {
        DefaultExportWindowsResourcesUseCase(
            dispatchers = get(),
        )
    }
}
