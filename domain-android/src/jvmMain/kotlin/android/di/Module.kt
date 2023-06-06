package android.di

import android.usecase.ExportAndroidResourcesUseCase
import android.usecase.ParseAndroidResourcesUseCase
import org.koin.dsl.module

val androidModule = module {
    single {
        ParseAndroidResourcesUseCase()
    }
    single {
        ExportAndroidResourcesUseCase()
    }
}
