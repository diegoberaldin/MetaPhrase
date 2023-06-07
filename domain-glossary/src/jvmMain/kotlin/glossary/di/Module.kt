package glossary.di

import glossary.repo.DefaultGlossaryTermRepository
import glossary.repo.GlossaryTermRepository
import glossary.usecase.DefaultGetGlossaryTermsUseCase
import glossary.usecase.GetGlossaryTermsUseCase
import org.koin.dsl.module

val glossaryModule = module {
    single<GlossaryTermRepository> {
        DefaultGlossaryTermRepository(
            dao = get(),
        )
    }
    single<GetGlossaryTermsUseCase> {
        DefaultGetGlossaryTermsUseCase(
            repository = get(),
            dispatchers = get(),
        )
    }
}
