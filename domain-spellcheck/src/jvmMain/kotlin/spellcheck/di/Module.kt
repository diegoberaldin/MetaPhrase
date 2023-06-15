package spellcheck.di

import org.koin.dsl.module
import spellcheck.repo.DefaultSpellCheckRepository
import spellcheck.repo.SpellCheckRepository
import spellcheck.spelling.DefaultSpelling
import spellcheck.spelling.Spelling
import spellcheck.usecase.DefaultValidateSpellingUseCase
import spellcheck.usecase.ValidateSpellingUseCase

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
