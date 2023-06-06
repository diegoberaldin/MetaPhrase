package glossary.di

import glossary.repo.DefaultGlossaryTermRepository
import glossary.repo.GlossaryTermRepository
import org.koin.dsl.module

val glossaryModule = module {
    single<GlossaryTermRepository> {
        DefaultGlossaryTermRepository(
            dao = get(),
        )
    }
}
