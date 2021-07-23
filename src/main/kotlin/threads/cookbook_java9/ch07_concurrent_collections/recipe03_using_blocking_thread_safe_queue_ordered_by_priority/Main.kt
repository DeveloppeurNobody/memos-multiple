package threads.cookbook_java9.ch07_concurrent_collections.recipe03_using_blocking_thread_safe_queue_ordered_by_priority

import threads.cookbook_java9.ch07_concurrent_collections.recipe03_using_blocking_thread_safe_queue_ordered_by_priority.task.Event
import threads.cookbook_java9.ch07_concurrent_collections.recipe03_using_blocking_thread_safe_queue_ordered_by_priority.task.Task
import java.util.concurrent.PriorityBlockingQueue

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // priority queue to store the events
        val queue = PriorityBlockingQueue<Event>();

        // an array to store the five Thread objects
        val taskThreads = arrayOfNulls<Thread>(5);

        // Create the five thres to execute five tasks
        taskThreads.indices
            .forEach {
                val task = Task(it, queue);
                taskThreads[it] = Thread(task);
            }

        // Start the five threads
        taskThreads.forEach { it?.start(); }

        // Wait for the finalization of the five threads
        taskThreads.forEach {
            try {
                it?.join();
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        }

        // Write the events in the console
        println("Main: Queue Size: ${queue.size}");
        taskThreads.indices
            .forEach {
                val event = queue.poll();
                println("Thread ${event.thread}: Priority ${event.priority}");
            }

        println("Main: Queue Size: ${queue.size}");
        println("Main: End of program");
    }
}