package ios.di

import ios.usecase.ExportIosResourcesUseCase
import ios.usecase.ParseIosResourcesUseCase
import org.koin.dsl.module

val iosModule = module {
    single {
        ParseIosResourcesUseCase()
    }
    single {
        ExportIosResourcesUseCase()
    }
}
