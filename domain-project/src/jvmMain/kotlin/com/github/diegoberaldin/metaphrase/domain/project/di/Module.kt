package com.github.diegoberaldin.metaphrase.domain.project.di

import com.github.diegoberaldin.metaphrase.domain.project.repository.di.projectRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.project.usecase.di.projectUseCaseModule
import org.koin.dsl.module

/**
 * DI module for the domain-project subproject.
 */
val projectModule = module {
    includes(
        projectRepositoryModule,
        projectUseCaseModule,
    )
}
