package threads.cookbook_java9.ch07_concurrent_collections.recipe03_using_blocking_thread_safe_queue_ordered_by_priority.task

import java.util.concurrent.PriorityBlockingQueue

class Task(val id: Int,
           val queue: PriorityBlockingQueue<Event>) : Runnable {

    override fun run() {
        repeat(1000) {
            val event = Event(id, it);
            queue.add(event);
        }
    }
}