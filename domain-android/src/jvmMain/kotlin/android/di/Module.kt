package android.di

import android.usecase.DefaultExportAndroidResourcesUseCase
import android.usecase.DefaultParseAndroidResourcesUseCase
import android.usecase.ExportAndroidResourcesUseCase
import android.usecase.ParseAndroidResourcesUseCase
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
