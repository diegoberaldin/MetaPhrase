package com.github.diegoberaldin.metaphrase.domain.mt.repository.di

import com.github.diegoberaldin.metaphrase.domain.mt.repository.DefaultMachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.client
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.deepl.DeeplDataSource
import com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.MyMemoryDataSource
import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * DI module for the domain-mt repository subproject.
 */
val machineTranslationRepositoryModule = module {
    single<HttpClient> {
        client
    }
    single<MachineTranslationRepository> {
        DefaultMachineTranslationRepository(
            myMemoryDataSource = get(),
            deeplDataSource = get(),
        )
    }
    single {
        MyMemoryDataSource(
            client = get(),
        )
    }
    single {
        DeeplDataSource()
    }
}
