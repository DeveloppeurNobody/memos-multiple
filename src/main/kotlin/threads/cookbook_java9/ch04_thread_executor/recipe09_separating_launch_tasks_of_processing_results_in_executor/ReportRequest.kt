package threads.cookbook_java9.ch04_thread_executor.recipe09_separating_launch_tasks_of_processing_results_in_executor

import java.util.concurrent.CompletionService

/**
 * This class represents every actor that can request a report.
 * For this example, it simply create three ReportGenerator objects and execute them through
 * a CompletionService
 */
class ReportRequest(val name: String,
                    val service: CompletionService<String>) : Runnable {

    /**
     * main method of the class. Create three ReportGenerator tasks
     * and executes them through a CompletionService
     */
    override fun run() {
        val reportGenerator = ReportGenerator(name, "Report");
        service.submit(reportGenerator);
    }
}