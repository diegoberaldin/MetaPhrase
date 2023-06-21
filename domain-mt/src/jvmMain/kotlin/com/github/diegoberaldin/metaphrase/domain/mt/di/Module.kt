package com.github.diegoberaldin.metaphrase.domain.mt.di

import com.github.diegoberaldin.metaphrase.domain.mt.repository.di.machineTranslationRepositoryModule
import org.koin.dsl.module

val machineTranslationModule = module {
    includes(machineTranslationRepositoryModule)
}
