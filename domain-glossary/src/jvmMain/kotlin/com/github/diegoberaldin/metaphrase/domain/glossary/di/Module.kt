package com.github.diegoberaldin.metaphrase.domain.glossary.di

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.di.glossaryRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.di.glossaryUseCaseModule
import org.koin.dsl.module

val glossaryModule = module {
    includes(
        glossaryRepositoryModule,
        glossaryUseCaseModule,
    )
}
