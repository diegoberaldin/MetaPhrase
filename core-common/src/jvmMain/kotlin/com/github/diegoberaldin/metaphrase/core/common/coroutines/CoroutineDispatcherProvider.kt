package com.github.diegoberaldin.metaphrase.core.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Injectable coroutine dispatcher provider to allow dynamic dispatcher replacement.
 */
interface CoroutineDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
