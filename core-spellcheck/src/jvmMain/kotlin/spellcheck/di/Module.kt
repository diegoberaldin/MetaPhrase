package spellcheck.di

import org.koin.dsl.module
import spellcheck.spelling.DefaultSpelling
import spellcheck.spelling.Spelling
import spellcheck.repo.DefaultSpellCheckRepository
import spellcheck.repo.SpellCheckRepository

internal val spellCheckInternalModule = module {
    single<Spelling> { DefaultSpelling() }
}

val spellcheckModule = module {
    includes(spellCheckInternalModule)
    single<SpellCheckRepository> {
        DefaultSpellCheckRepository(
            spelling = get(),
        )
    }
}
