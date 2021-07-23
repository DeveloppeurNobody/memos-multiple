package javafx_multiple.event

import javafx.event.EventHandler

class AudioPlayerService : AudioPlayer {
    val eventBus = ServiceLocator.getService(EventBus::class.java);

    init {
        eventBus.addEventHandler(GameEvent.PLAYER_DIED, EventHandler { event -> playsound("Player Death") })
    }

    override fun playsound(name: String){
        println("Playing sound: $name");
    }
}

