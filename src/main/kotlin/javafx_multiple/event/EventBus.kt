package javafx_multiple.event

import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType

interface EventBus {
    fun fireEvent(event: Event);

    fun <T: Event> addEventHandler(type: EventType<T>, handler: EventHandler<in T>);
    fun <T: Event> removeEventHandler(type: EventType<T>, handler: EventHandler<in T>);
}