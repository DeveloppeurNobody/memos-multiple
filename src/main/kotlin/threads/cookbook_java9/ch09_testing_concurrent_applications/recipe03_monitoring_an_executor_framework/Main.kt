package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe03_monitoring_an_executor_framework

import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe03_monitoring_an_executor_framework.executor.Task
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


/**
 * Main class of the example. Create an Executor and submits ten Task
 * objects for its execution. It writes information about the executor
 * to see its evolution.
 *
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new Executor
        val executor = Executors.newCachedThreadPool() as ThreadPoolExecutor;

        // Create and submit ten tasks
        val random = java.util.Random();
        repeat(10) {
            val task = Task(random.nextInt(1000).toLong());
            executor.submit(task);
        }

        // Write information about the executor
        repeat(5) {
            showLog(executor);
            TimeUnit.SECONDS.sleep(1);
        }

        // Shutdown the executor
        executor.shutdown();

        repeat(5) {
            showLog(executor);
            TimeUnit.SECONDS.sleep(1);
        }

        // Wait for the finalisation of the executor
        executor.awaitTermination(1, TimeUnit.DAYS);

        // Write a message to indicate the of the program.
        println("Main: End of the program.\n");

    }

    /**
     * Method that writes in the console information about an executor
     * @param executor Executor this method is going to process
     */
    private fun showLog(executor: ThreadPoolExecutor) {
        println("""
            Main: Executor Log
            Main: Executor: Core Pool Size: ${executor.corePoolSize}
            Main: Executor: Pool Size: ${executor.poolSize}
            Main: Executor: Active Count: ${executor.activeCount}
            Main: Executor: Task Count: ${executor.taskCount}
            Main: Executor: Completed Task Count: ${executor.completedTaskCount}
            Main: Executor: Shutdown: ${executor.isShutdown}
            Main: Executor: Terminating: ${executor.isTerminating}
            Main: Executor: Terminated: ${executor.isTerminated}
        """.trimIndent());
    }
}