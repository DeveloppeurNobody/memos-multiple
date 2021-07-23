package threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.task

import threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.event.Event
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Runnable class that generates an event every second
 */
class WriterTask(var deque: Deque<Event>) : Runnable {

    override fun run() {

        // Writes 100 events
        (0 until 100).forEach {
            // Creates and initializes the Event Objects
            val event =
                Event(
                    Date(),
                    "The Thread ${Thread.currentThread().id} has generates the event $it"
                );

            // Add to the data structure
            deque.addFirst(event);

            try {
                // Sleeps during one second
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}