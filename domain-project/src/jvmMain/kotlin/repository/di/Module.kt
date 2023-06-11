package repository.di

import org.koin.dsl.module
import projectrepository.di.projectRepositoryModule
import projectusecase.di.projectUseCaseModule

val projectModule = module {
    includes(
        projectRepositoryModule,
        projectUseCaseModule,
    )
}
