package com.github.diegoberaldin.metaphrase.domain.tm.di

import com.github.diegoberaldin.metaphrase.domain.tm.repository.di.tmRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.di.tmUseCaseModule
import org.koin.dsl.module

/**
 * DI module for the translation memory domain subproject.
 */
val translationMemoryModule = module {
    includes(
        tmRepositoryModule,
        tmUseCaseModule,
    )
}
