package common.notification

import kotlinx.coroutines.flow.SharedFlow

interface NotificationCenter {

    sealed interface Event {
        data class ShowProgress(val visible: Boolean) : Event
    }

    val events: SharedFlow<Event>

    fun send(event: Event)
}
