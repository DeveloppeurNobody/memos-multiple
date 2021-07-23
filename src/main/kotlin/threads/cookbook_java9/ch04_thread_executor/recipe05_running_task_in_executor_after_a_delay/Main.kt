package threads.cookbook_java9.ch04_thread_executor.recipe05_running_task_in_executor_after_a_delay

import threads.cookbook_java9.ch04_thread_executor.recipe05_running_task_in_executor_after_a_delay.task.Task
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a ScheduledThreadPoolExecutor
        val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1);
        println("Main: Starting at ${Date()}");

        // Send the taks to the executor with the specified delay
        repeat(5) {
            val task = Task("Task-$it");
            executor.schedule(task, (it+1).toLong(), TimeUnit.SECONDS);
        }

        // Finish the executor
        executor.shutdown();

        // Waits for the finalization of the executor
        try {
            executor.awaitTermination(1L, TimeUnit.DAYS);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Writes the finalization message
        println("Core: Ends at ${Date()}");
    }
}