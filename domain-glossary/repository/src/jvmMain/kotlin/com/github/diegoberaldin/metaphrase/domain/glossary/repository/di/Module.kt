package com.github.diegoberaldin.metaphrase.domain.glossary.repository.di

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.DefaultGlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import org.koin.dsl.module

/**
 * DI module for the domain-glossary repository subproject.
 */
val glossaryRepositoryModule = module {
    single<GlossaryTermRepository> {
        DefaultGlossaryTermRepository(
            dao = get(),
        )
    }
}
