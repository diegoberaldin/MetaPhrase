package com.github.diegoberaldin.metaphrase.core.common.coroutines

import kotlinx.coroutines.Dispatchers

internal object DefaultCoroutineDispatcherProvider :
    com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
    override val unconfined = Dispatchers.Unconfined
}
