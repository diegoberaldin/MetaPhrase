package com.github.diegoberaldin.metaphrase.domain.language.repository.di

import com.github.diegoberaldin.metaphrase.domain.language.repository.DefaultFlagsRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.DefaultLanguageNameRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.DefaultLanguageRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.FlagsRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageNameRepository
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import org.koin.dsl.module

/**
 * DI module for the domain-language repository subproject.
 */
val languageRepositoryModule = module {
    single<LanguageRepository> {
        DefaultLanguageRepository(
            dao = get(),
        )
    }
    single<LanguageNameRepository> {
        DefaultLanguageNameRepository()
    }
    single<FlagsRepository> {
        DefaultFlagsRepository()
    }
}
