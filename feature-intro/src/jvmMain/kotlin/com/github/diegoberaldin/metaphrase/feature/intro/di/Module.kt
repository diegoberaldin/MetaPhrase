package com.github.diegoberaldin.metaphrase.feature.intro.di

import com.github.diegoberaldin.metaphrase.feature.intro.presentation.DefaultIntroComponent
import com.github.diegoberaldin.metaphrase.feature.intro.presentation.IntroComponent
import org.koin.dsl.module

val introModule = module {
    factory<IntroComponent> { params ->
        DefaultIntroComponent(
            componentContext = params[0],
            coroutineContext = params[1],
        )
    }
}
