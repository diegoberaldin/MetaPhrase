package com.github.diegoberaldin.metaphrase.domain.spellcheck.di

import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.DefaultSpellCheckRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.DefaultUserDefinedWordsRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.UserDefinedWordsRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.DefaultSpelling
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.Spelling
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.DefaultValidateSpellingUseCase
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.ValidateSpellingUseCase
import org.koin.dsl.module

/**
 * DI module for the domain spellcheck subproject.
 */
private val spellCheckInternalModule = module {
    single<UserDefinedWordsRepository> {
        DefaultUserDefinedWordsRepository(
            dispatchers = get(),
            fileManager = get(),
        )
    }
    factory<Spelling> {
        DefaultSpelling(
            userDefinedWordsRepository = get(),
        )
    }
}

val spellcheckModule = module {
    includes(spellCheckInternalModule)
    single<SpellCheckRepository> {
        DefaultSpellCheckRepository(
            spelling = get(),
            dispatchers = get(),
        )
    }
    single<ValidateSpellingUseCase> {
        DefaultValidateSpellingUseCase(
            repository = get(),
            dispatchers = get(),
        )
    }
}
