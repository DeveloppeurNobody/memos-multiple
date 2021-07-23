package javafx_multiple.event_ftp

import javafx.event.EventHandler

class DefaultCommandService : CommandService {
    val eventBus = ServiceLocatorFtp.getService(EventBus::class.java);

    init {
        eventBus.addEventHandler(CommandEvent.PWD, EventHandler { event: CommandEvent? ->
            runCommand("PWD");
        })
    }

    override fun runCommand(name: String) {
        println("runCommand() #name: $name")
    }
}