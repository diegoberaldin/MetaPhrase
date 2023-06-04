package spellcheck

import org.koin.dsl.module

val spellcheckModule = module {
    single<SpellCheckRepository> { DefaultSpellCheckRepository() }
}
