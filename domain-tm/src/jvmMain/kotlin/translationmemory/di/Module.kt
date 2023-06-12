package translationmemory.di

import org.koin.dsl.module
import tmrepository.di.tmRepositoryModule
import tmusecase.di.tmUseCaseModule

val translationMemoryModule = module {
    includes(
        tmRepositoryModule,
        tmUseCaseModule,
    )
}
