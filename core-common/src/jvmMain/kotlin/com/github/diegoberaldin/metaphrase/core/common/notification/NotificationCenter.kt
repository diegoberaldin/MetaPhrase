package com.github.diegoberaldin.metaphrase.core.common.notification

import kotlinx.coroutines.flow.SharedFlow

/**
 * Utility to publish and subscribe for broadcast notifications.
 */
interface NotificationCenter {

    /**
     * Available event types.
     */
    sealed interface Event {
        data class ShowProgress(val visible: Boolean) : Event
    }

    /**
     * Observable event flow
     */
    val events: SharedFlow<Event>

    /**
     * Publish and event to subscribers.
     *
     * @param event Event to send
     */
    fun send(event: Event)
}
