package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.core

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.task.MyRecursiveTask
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.task.MyWorkerThreadFactory
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new MyWorkerThreadFactory
        val factory = MyWorkerThreadFactory();

        // Create new ForkJoinPool, passing as parameter the factory created before
        val pool = ForkJoinPool(4, factory, null, false);

        // Create and initializes the elements of the array
        val array = Array<Int>(10000) { 1 };

        // Create a new task to sum the elements of the array
        val task = MyRecursiveTask(array, 0, array.size);

        // Send the task to the ForkJoinPool
        pool.execute(task);

        // Wait for the finalization of the task
        task.join();

        // Shutdown the pool
        pool.shutdown();

        // Wait for the finalization of the pool
        pool.awaitTermination(1, TimeUnit.DAYS);

        // Write the result of the task
        println("Main: Result: ${task.get()}");

        // Write a message indicating the end of the program
        println("Main: End of the program.");

    }
}