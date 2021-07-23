package javafx_multiple.event

import javafx.event.Event
import javafx.event.EventType

class GameEvent : Event {

    companion object {
        val ANY: EventType<GameEvent> = EventType(Event.ANY, "GAME_EVENT");
        val PLAYER_DIED: EventType<GameEvent> = EventType(Event.ANY, "PLAYER_DIED");
    }

    constructor(type: EventType<GameEvent>): super(type) {}
}