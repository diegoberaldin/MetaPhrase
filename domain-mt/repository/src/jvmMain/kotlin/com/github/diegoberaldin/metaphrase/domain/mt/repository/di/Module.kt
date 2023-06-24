package com.github.diegoberaldin.metaphrase.domain.mt.repository.di

import com.github.diegoberaldin.metaphrase.domain.mt.repository.DefaultMachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.MyMemoryDataSource
import org.koin.dsl.module

/**
 * DI module for the domain-mt repository subproject.
 */
val machineTranslationRepositoryModule = module {
    single<MachineTranslationRepository> {
        DefaultMachineTranslationRepository(
            myMemoryDataSource = get(),
        )
    }
    single {
        MyMemoryDataSource()
    }
}
