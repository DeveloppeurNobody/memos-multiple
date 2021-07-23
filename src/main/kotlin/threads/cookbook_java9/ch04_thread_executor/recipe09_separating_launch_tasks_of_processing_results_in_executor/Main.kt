package threads.cookbook_java9.ch04_thread_executor.recipe09_separating_launch_tasks_of_processing_results_in_executor

import java.util.concurrent.CompletionService
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create the executor and three completionService using that executor
        val executor = Executors.newCachedThreadPool();
        val service: CompletionService<String> = ExecutorCompletionService(executor);

        // Create two ReportRequest object and two Threads to execute Them
        val faceRequest = ReportRequest("Face", service);
        val onlineRequest = ReportRequest("Online", service);
        val faceThread = Thread(faceRequest);
        val onlineThread = Thread(onlineRequest);

        // Create a ReportSender object and a Thread to execute it
        val processor = ReportProcessor(service);
        val senderThread = Thread(processor);

        // Start the Threads
        println("Main: Starting The Threads");
        faceThread.start();
        onlineThread.start();
        senderThread.start();

        // Wait for the end of the ReportGenerator tasks
        try {
            println("Main: Wainting for the report generators");
            faceThread.join();
            onlineThread.join();
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Shutdown the executor
        println("Main: Shuting down the executor");
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // End the execution of the ReportSender
        processor.stopProcessing();
        println("Main: Ends");
    }
}