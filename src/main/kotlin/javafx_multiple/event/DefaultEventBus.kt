package javafx_multiple.event

import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.Group

class DefaultEventBus : EventBus {
    private var group = Group();

    override fun fireEvent(event: Event) {
        println("FireEvent $event");
        group.fireEvent(event);
    }

    override fun <T : Event> addEventHandler(type: EventType<T>, handler: EventHandler<in T>) {
        // delegating event to group
        group.addEventHandler(type, handler);
    }

    override fun <T : Event> removeEventHandler(type: EventType<T>, handler: EventHandler<in T>) {
        group.removeEventHandler(type, handler);
    }
}