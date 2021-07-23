package threads.cookbook_java9.ch04_thread_executor.recipe07_cancelling_task_in_executor

import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create an executor
        val executor = Executors.newCachedThreadPool() as ThreadPoolExecutor;

        // Create a task
        val task = Task();

        println("Main: Executing the Task");

        // Send the task to the executor
        val result: Future<String> = executor.submit(task);

        // Sleep during two seconds
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Cancel the task, finishing its execution
        println("Main: Cancelling the Task");
        result.cancel(true);

        // Verify that the task has been cancelled
        println("Main: Cancelled: ${result.isCancelled}");
        println("Main: Done: ${result.isDone}");

        // Shutdown the executor
        executor.shutdown();
        println("Main: The executor has finished");
    }
}