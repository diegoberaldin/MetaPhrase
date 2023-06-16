package com.github.diegoberaldin.metaphrase.domain.tm.di

import com.github.diegoberaldin.metaphrase.domain.tm.repository.di.tmRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.di.tmUseCaseModule
import org.koin.dsl.module

val translationMemoryModule = module {
    includes(
        tmRepositoryModule,
        tmUseCaseModule,
    )
}
