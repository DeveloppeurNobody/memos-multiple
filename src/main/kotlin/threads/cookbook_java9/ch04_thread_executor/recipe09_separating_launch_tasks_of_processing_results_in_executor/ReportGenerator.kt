package threads.cookbook_java9.ch04_thread_executor.recipe09_separating_launch_tasks_of_processing_results_in_executor

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * This class simulates the generation of a report.
 * Is a Callable object that will be executed by the executor
 * inside a CompletionService
 */
class ReportGenerator(val sender: String,
                      val title: String) : Callable<String> {

    @Throws(Exception::class)
    override fun call(): String {
        try {
            val duration = (Math.random() * 10).toLong();
            println("${sender}_${title}: ReportGenerator: Generating a report during $duration seconds");
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        return "${sender}:$title";
    }
}