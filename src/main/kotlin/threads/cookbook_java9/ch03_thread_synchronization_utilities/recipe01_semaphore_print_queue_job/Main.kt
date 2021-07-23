package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe01_semaphore_print_queue_job

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe01_semaphore_print_queue_job.task.Job
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe01_semaphore_print_queue_job.task.PrintQueue

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the print queue
        val printQueue = PrintQueue();

        // Creates twelve Threads
        val threads = arrayOfNulls<Thread>(12);

        threads.indices
            .forEach {
            threads[it] = Thread(Job(printQueue), "Thread $it");
        }

        threads.indices
            .forEach {
                threads[it]?.start();
            }
    }
}