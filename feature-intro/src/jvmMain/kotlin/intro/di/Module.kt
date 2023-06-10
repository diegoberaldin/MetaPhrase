package intro.di

import intro.presentation.DefaultIntroComponent
import intro.presentation.IntroComponent
import org.koin.dsl.module

val introModule = module {
    factory<IntroComponent> { params ->
        DefaultIntroComponent(
            componentContext = params[0],
            coroutineContext = params[1],
        )
    }
}
