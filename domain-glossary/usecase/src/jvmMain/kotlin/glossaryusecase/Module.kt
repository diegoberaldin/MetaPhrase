package glossaryusecase

import org.koin.dsl.module

val glossaryUseCaseModule = module {
    single<GetGlossaryTermsUseCase> {
        DefaultGetGlossaryTermsUseCase(
            repository = get(),
            dispatchers = get(),
            spelling = get(),
        )
    }
}
