package glossary.di

import glossaryrepository.glossaryRepositoryModule
import glossaryusecase.glossaryUseCaseModule
import org.koin.dsl.module

val glossaryModule = module {
    includes(
        glossaryRepositoryModule,
        glossaryUseCaseModule,
    )
}
