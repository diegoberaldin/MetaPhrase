package com.github.diegoberaldin.metaphrase.domain.language.di

import com.github.diegoberaldin.metaphrase.domain.language.repository.di.languageRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.language.usecase.di.languageUseCaseModule
import org.koin.dsl.module

/**
 * DI module of the domain-language subproject.
 */
val languageModule = module {
    includes(
        languageRepositoryModule,
        languageUseCaseModule,
    )
}
