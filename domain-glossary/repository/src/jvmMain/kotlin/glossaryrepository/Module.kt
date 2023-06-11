package glossaryrepository

import org.koin.dsl.module

val glossaryRepositoryModule = module {
    single<GlossaryTermRepository> {
        DefaultGlossaryTermRepository(
            dao = get(),
        )
    }
}
