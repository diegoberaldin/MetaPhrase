package com.github.diegoberaldin.metaphrase.domain.project.di

import com.github.diegoberaldin.metaphrase.domain.project.repository.di.projectRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.project.usecase.di.projectUseCaseModule
import org.koin.dsl.module

val projectModule = module {
    includes(
        projectRepositoryModule,
        projectUseCaseModule,
    )
}
