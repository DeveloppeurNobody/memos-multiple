package threads.cookbook_java9.ch05_fork_join_framework.recipe05_throwing_exceptions_in_tasks.core

import threads.cookbook_java9.ch05_fork_join_framework.recipe05_throwing_exceptions_in_tasks.task.Task
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Array of 100 integers
        val array = arrayOfNulls<Int>(100);

        // Task to process the array
        val task = Task(array, 0, 100);

        // ForkJoinPool to execute the Task
        val pool = ForkJoinPool();

        // Execute the task
        pool.execute(task);

        // Shutdown the ForkJoinPool
        pool.shutdown();

        // Wait fot the finalization of the task
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Check if the task ha throw an Exception. If it's the case, write it
        // to the console

        if (task.isCompletedAbnormally) {
            println("Main: An exception has occured");
            println("Main: ${task.exception}");
        }

        println("Main: Result: ${task.join()}");
    }
}