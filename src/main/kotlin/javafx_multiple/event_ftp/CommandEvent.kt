package javafx_multiple.event_ftp

import javafx.event.Event
import javafx.event.EventType


class CommandEvent : Event {
    companion object {
        val ANY: EventType<CommandEvent> = EventType(Event.ANY, "COMMAND_EVENT");
        val PWD: EventType<CommandEvent> = EventType(Event.ANY, "PWD");
    }

    constructor(type: EventType<CommandEvent>) : super(type) {
    }

}