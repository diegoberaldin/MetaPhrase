package intro.ui

import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

internal class DefaultIntroComponent(componentContext: ComponentContext, coroutineContext: CoroutineContext) :
    IntroComponent, ComponentContext by componentContext
