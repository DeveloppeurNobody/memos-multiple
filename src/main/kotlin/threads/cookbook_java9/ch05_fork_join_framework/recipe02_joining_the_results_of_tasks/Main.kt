package threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks

import threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks.task.DocumentTask
import threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks.util.DocumentMock
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Generate a document with 100 lines and 1000 words per line
        val mock = DocumentMock();
        val document = mock.generateDocument(100, 1000, "the");

        // Create a DocumentTask
        val task = DocumentTask(document, 0, 100, "the");

        val commonPool: ForkJoinPool = ForkJoinPool.commonPool();

        // Execute the Task
        commonPool.execute(task);

        // Write statictis about the pool
        do {
            println("""
                *****************************************************
                Main: Parallelism: ${commonPool.parallelism}
                Main: Active Threads: ${commonPool.activeThreadCount}
                Main: Task Count: ${commonPool.queuedTaskCount}
                Main: Steal Count: ${commonPool.stealCount}
                *****************************************************
            """.trimIndent());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        } while (!task.isDone);

        // Shutdown the pool
        commonPool.shutdown();

        // Wait for the finalization of the tasks
        try {
            commonPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Write the result of the tasks
        try {
            println("Main: The word appears ${task.get()} in the document");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

    }
}