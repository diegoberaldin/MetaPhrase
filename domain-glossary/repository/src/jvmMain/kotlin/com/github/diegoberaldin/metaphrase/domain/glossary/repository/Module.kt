package com.github.diegoberaldin.metaphrase.domain.glossary.repository

import org.koin.dsl.module

val glossaryRepositoryModule = module {
    single<GlossaryTermRepository> {
        DefaultGlossaryTermRepository(
            dao = get(),
        )
    }
}
