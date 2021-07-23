package threads.cookbook_java9.ch07_concurrent_collections.recipe01_non_blocking_thread_safe_deques

import threads.cookbook_java9.ch07_concurrent_collections.recipe01_non_blocking_thread_safe_deques.task.AddTask
import threads.cookbook_java9.ch07_concurrent_collections.recipe01_non_blocking_thread_safe_deques.task.PollTask
import java.util.concurrent.ConcurrentLinkedDeque

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a ConcurrentLinkedDeque to work with it in the example
        val list = ConcurrentLinkedDeque<String>();

        // Create an Array of 100 threads
        val threads = arrayOfNulls<Thread>(100);

        // Create 100 AddTask and execute them as threads
        (threads.indices).forEach {
            val task = AddTask(list);
            threads[it] = Thread(task);
            threads[it]?.start();
        }
        println("Main: ${threads.size} AddTask threads have been launched");

        // Wait for the finalization of threads
        threads.forEach {
            it?.join();
            println("addTask done!");
        }

        // Write to the console the size of the list
        println("Main: Size of the List: ${list.size}");

        // Create 100 PollTasks objects and execute them as threads
        threads.indices
            .forEach {
                val pollTask = PollTask(list);
                threads[it] = Thread(pollTask);
                threads[it]?.start();
            }
        println("Main: ${threads.size} PollTask threads have been launched\n");

        // Wait for the finalization of the threads
        threads.forEach {
            it?.join();
            println("pollTask done! ");
        }

        // Write to the console the size of the list
        println("Main: Size of the List: ${list.size}");
    }
}