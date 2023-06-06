package spellcheck.di

import org.koin.dsl.module
import spellcheck.repo.DefaultSpellCheckRepository
import spellcheck.repo.SpellCheckRepository
import spellcheck.spelling.DefaultSpelling
import spellcheck.spelling.Spelling

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
