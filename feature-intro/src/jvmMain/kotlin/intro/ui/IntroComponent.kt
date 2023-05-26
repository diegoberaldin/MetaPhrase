package intro.ui

import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

interface IntroComponent {
    companion object {
        fun newInstance(componentContext: ComponentContext, coroutineContext: CoroutineContext): IntroComponent =
            DefaultIntroComponent(
                componentContext = componentContext,
                coroutineContext = coroutineContext,
            )
    }
}
