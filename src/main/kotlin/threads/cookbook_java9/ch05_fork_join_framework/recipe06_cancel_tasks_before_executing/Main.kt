package threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing

import threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.task.SearchNumberTask
import threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.util.ArrayGenerator
import threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.util.TaskManager
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

/**
 * The ForkJoinTask class provides the cancel() method that allows you to cancel a task if
 * it hasn't been executed yet. This is a very important point. If the task has begun its
 * execution, a call to the cancel() method has no effect.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Generate an array of 1000 integers
        val generator = ArrayGenerator();
        val array = generator.generateArray(1000);

        // Create a TaskManager Object
        val manager = TaskManager();

        // Create a ForkJoinPool with the default constructor
        val pool = ForkJoinPool();

        // Create a Task to process the array
        val task = SearchNumberTask(array, 0, 1000, 5, manager);

        // Execute the task
        pool.execute(task);

        // Shutdown the pool
        pool.shutdown();

        // Wait for the finalization of the task
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Write a message to indicate the end of the program
        println("Main: The program has finished");
    }
}