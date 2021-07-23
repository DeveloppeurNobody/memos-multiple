package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe04_monitoring_a_fork_join_pool_framework

import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe04_monitoring_a_fork_join_pool_framework.forkjoin.Task
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

/**
 * Main class of the example. It creates all the elements for the
 * execution and writes information about the Fork/Join pool that
 * executes the task
 *
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create the Fork/Join pool
        val pool = ForkJoinPool();

        // Create a new Array of integer numbers with 10000 elements
        val array = Array<Int>(10000) { 0 }

        // Create a new task (it is the first or root task)
        val task1 = Task(array, 0, array.size);

        // Execute the task in the Fork/Join pool
        pool.execute(task1);

        // Wait for the finalization of the task writing information
        while (!task1.isDone) {
            showLog(pool);
            TimeUnit.SECONDS.sleep(1);
        }

        // Shutdown the pool
        pool.shutdown();

        // Wait for the finalization of the pool
        pool.awaitTermination(1, TimeUnit.DAYS);

        // End of the program.
        showLog(pool);
        println("Main: End of program.\n");

    }

    /**
     * that method writes information about a Fork/Join pool
     */
    private fun showLog(pool: ForkJoinPool) {
        println("""
            **************************************************
            Main: Fork/Join Pool log
            Main: Fork/Join Pool: Parallelism: ${pool.parallelism}
            Main: Fork/Join Pool: Pool Size: ${pool.poolSize}
            Main: Fork/Join Pool: Active Thread Count: ${pool.activeThreadCount}
            Main: Fork/Join Pool: Running Thread Count: ${pool.runningThreadCount}
            Main: Fork/Join Pool: Queued Submission Count: ${pool.queuedSubmissionCount}
            Main: Fork/Join Pool: Queued Tasks: ${pool.queuedTaskCount}
            Main: Fork/Join Pool: Has Queued Submissions: ${pool.hasQueuedSubmissions()}
            Main: Fork/Join Pool: Steal Count: ${pool.stealCount}
            Main: Fork/Join Pool: Terminated: ${pool.isTerminated}
            **************************************************
        """.trimIndent());
    }
}