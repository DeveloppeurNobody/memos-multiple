package threads.cookbook_java9.ch07_concurrent_collections.recipe04_using_thread_safe_lists_with_delayed_elements.task

import java.util.*
import java.util.concurrent.DelayQueue

class Task(val id: Int,
           val queue: DelayQueue<Event>) : Runnable {

    override fun run() {
        val now = Date();
        val delay = Date();
        delay.time = (now.time + (id * 1000));

        println("Thread $id: $delay");

        repeat(100) {
            val event = Event(delay);
            queue.add(event);
        }
    }
}