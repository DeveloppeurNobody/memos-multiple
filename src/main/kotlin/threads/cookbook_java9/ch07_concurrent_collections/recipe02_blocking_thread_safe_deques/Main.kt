package threads.cookbook_java9.ch07_concurrent_collections.recipe02_blocking_thread_safe_deques

import java.util.*
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

/**
 * Main class of the example. First, execute 100 AddTask objects to add 1000000
 * elements to the list and the execute 100 PollTask objects to delete all those
 * elements.
 *
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a ConcurrentLinkedDeque to work with it in the example
        val list = LinkedBlockingDeque<String>(3);

        val client = Client(list);
        val thread = Thread(client);
        thread.start();

        repeat(5) { i ->
            repeat(3) { j ->
                val request = list.take();
                println("Main: Removed: $request at ${Date()}. Size: ${list.size}");
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
        println("Main: End of the program.");
    }
}