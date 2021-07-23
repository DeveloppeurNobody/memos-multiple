package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe07_customizing_tasks_running_in_fork_join_framework

import java.util.concurrent.ForkJoinPool
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe07_customizing_tasks_running_in_fork_join_framework.task.*;
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create an array of 10000 elements
        val array = Array<Int>(10000) { 0 };

        // ForkJoinPool to execute the task
        val pool = ForkJoinPool();

        // Task to increment the elements of the array
        val task = Task("Task", array, 0, array.size);

        // Send the task to the pool
        pool.invoke(task);

        // Send the task to the pool
        pool.shutdown();

        // Write a message in the console.
        println("Main: End of program.");
    }
}