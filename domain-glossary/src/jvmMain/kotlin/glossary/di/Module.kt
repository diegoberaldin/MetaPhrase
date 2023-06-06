package glossary.di

import glossary.repo.GlossaryTermRepository
import org.koin.dsl.module

val glossaryModule = module {
    single {
        GlossaryTermRepository(
            dao = get(),
        )
    }
}
