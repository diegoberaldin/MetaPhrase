package glossary.di

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.glossaryRepositoryModule
import com.github.diegoberaldin.metaphrase.domain.glossary.usecase.glossaryUseCaseModule
import org.koin.dsl.module

val glossaryModule = module {
    includes(
        glossaryRepositoryModule,
        glossaryUseCaseModule,
    )
}
