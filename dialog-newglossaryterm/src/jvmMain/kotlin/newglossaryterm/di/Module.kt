package newglossaryterm.di

import newglossaryterm.presentation.DefaultNewGlossaryTermComponent
import newglossaryterm.presentation.NewGlossaryTermComponent
import org.koin.dsl.module

val dialogNewGlossaryTermModule = module {
    factory<NewGlossaryTermComponent> { params ->
        DefaultNewGlossaryTermComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
        )
    }
}
