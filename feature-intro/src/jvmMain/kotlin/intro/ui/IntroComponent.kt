package intro.ui

import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

interface IntroComponent {
    object Factory {
        fun create(componentContext: ComponentContext, coroutineContext: CoroutineContext): IntroComponent =
            DefaultIntroComponent(
                componentContext = componentContext,
                coroutineContext = coroutineContext,
            )
    }
}
