package formatsandroid.di

import formatsandroid.usecase.DefaultExportAndroidResourcesUseCase
import formatsandroid.usecase.DefaultParseAndroidResourcesUseCase
import formatsandroid.usecase.ExportAndroidResourcesUseCase
import formatsandroid.usecase.ParseAndroidResourcesUseCase
import org.koin.dsl.module

val androidModule = module {
    single<ParseAndroidResourcesUseCase> {
        DefaultParseAndroidResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportAndroidResourcesUseCase> {
        DefaultExportAndroidResourcesUseCase(
            dispatchers = get(),
        )
    }
}
