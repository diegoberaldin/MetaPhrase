package com.github.diegoberaldin.metaphrase.domain.spellcheck.di

import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.DefaultSpellCheckRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.SpellCheckRepository
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.DefaultSpelling
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.Spelling
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.DefaultValidateSpellingUseCase
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.ValidateSpellingUseCase
import org.koin.dsl.module

internal val spellCheckInternalModule = module {
    factory<Spelling> { DefaultSpelling() }
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
