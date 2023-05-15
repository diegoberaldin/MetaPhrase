package common.notification

import kotlinx.coroutines.flow.SharedFlow

interface NotificationCenter {

    sealed interface Event {
        data class OpenProject(val projectId: Int) : Event
        object CurrentProjectEdited : Event
    }

    val events: SharedFlow<Event>

    fun send(event: Event)
}
