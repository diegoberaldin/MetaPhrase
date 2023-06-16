package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.di

import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.DefaultNewGlossaryTermComponent
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.NewGlossaryTermComponent
import org.koin.dsl.module

val dialogNewTermModule = module {
    factory<NewGlossaryTermComponent> { params ->
        DefaultNewGlossaryTermComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
        )
    }
}
