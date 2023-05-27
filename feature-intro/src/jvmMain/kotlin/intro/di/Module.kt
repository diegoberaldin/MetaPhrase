package intro.di

import intro.ui.DefaultIntroComponent
import intro.ui.IntroComponent
import org.koin.dsl.module

val introModule = module {
    factory<IntroComponent> { params ->
        DefaultIntroComponent(
            componentContext = params[0],
            coroutineContext = params[1],
        )
    }
}
