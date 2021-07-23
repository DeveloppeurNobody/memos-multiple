package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe01_semaphore_print_queue_job.task

/**
 * This class simulates a job that send a document to print.
 */
class Job(private val printQueue: PrintQueue) : Runnable {

    override fun run() {
        println("${Thread.currentThread().name}: Going to print a job");
        printQueue.printJob(Any());
        println("${Thread.currentThread().name}: The document has been printed");
    }
}
