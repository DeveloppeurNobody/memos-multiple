package threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.task

import threads.cookbook_java9.ch01_thread_management.recipe06_daemon_thread.event.Event
import java.util.*

class CleanerTask(var deque: Deque<Event>) : Thread() {

    init {
        isDaemon = true;
    }

    override fun run() {
        while (true) {
            val date = Date();
            clean(date);
        }
    }

    /**
     * method that review the Events data structure and delete the events
     * older than ten seconds
     *
     * @param date
     */
    private fun clean(date: Date) {
        var difference: Long;
        var delete: Boolean = false;

        if (deque.size == 0) {
            return;
        }

        do {
            var event = deque.last;
            difference = date.time - event.date.time;

            if (difference > 10000) {
                println("Cleaner: ${event.event}");
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            println("Cleaner: Size of the queue: ${deque.size}")
        }

    }

}

