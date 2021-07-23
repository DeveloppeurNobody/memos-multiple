package threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread

import threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.event.Event
import threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.task.CleanerTask
import threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.task.WriterTask
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the Event data structure
        val deque: Deque<Event> = ConcurrentLinkedDeque();

        // Creates the three WriteTask and starts them
        val writer =
            WriterTask(
                deque
            );
        (0 until Runtime.getRuntime().availableProcessors()).forEach {
            Thread(writer).start();
        }

        // Creates a cleaner task and starts them
        CleanerTask(deque).start();
    }
}