package threads.cookbook_java9.ch04_thread_executor.recipe09_separating_launch_tasks_of_processing_results_in_executor

import java.util.concurrent.CompletionService
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

/**
 * This class will take the results of the ReportGenerator tasks executed through
 * a CompletionService
 */
class ReportProcessor(val service: CompletionService<String>) : Runnable {

    /**
     * Variable to store the status of the Object.
     * Il will executes until the variable takes the true value
     */
    @Volatile private var end: Boolean = false;

    override fun run() {
        while (!end) {
            try {
                val result = service.poll(20, TimeUnit.SECONDS);
                if (result != null) {
                    println("ReportProcessor: Report Received: ${result.get()}");
                }
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } catch (ee: ExecutionException) {
                System.err.println(ee);
            }

            println("ReportProcessor: End");
        }
    }

    /**
     * Method that establish the value of the end attribute
     * @param end New value of the end attribute
     */
    fun stopProcessing() {
        end = true;
    }
}