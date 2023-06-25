package com.github.diegoberaldin.metaphrase.core.common.notification

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class DefaultNotificationCenterTest {

    private val sut = DefaultNotificationCenter
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Test
    fun givenNotificationCenterWhenShowProgressEventSentThenItIsReceived() {
        var evt: NotificationCenter.Event? = null
        sut.events.onEach {
            evt = it
        }.launchIn(scope)
        runBlocking {
            sut.send(NotificationCenter.Event.ShowProgress(true))
            delay(100)
            assert(evt != null)
            assert(evt == NotificationCenter.Event.ShowProgress(true))
        }
    }
}
