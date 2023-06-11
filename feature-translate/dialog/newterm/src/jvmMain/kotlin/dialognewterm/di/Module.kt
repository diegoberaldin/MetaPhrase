package dialognewterm.di

import dialognewterm.presentation.DefaultNewGlossaryTermComponent
import dialognewterm.presentation.NewGlossaryTermComponent
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
