package com.github.diegoberaldin.metaphrase.core.common.notification

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DefaultNotificationCenterTest {
    private val sut = DefaultNotificationCenter

    @Test
    fun givenNotificationCenterWhenShowProgressEventSentThenItIsReceived() = runTest {
        sut.events.test {
            sut.send(NotificationCenter.Event.ShowProgress(true))
            val evt = awaitItem()
            assertNotNull(evt)
            assertEquals(NotificationCenter.Event.ShowProgress(true), evt)
        }
    }
}
