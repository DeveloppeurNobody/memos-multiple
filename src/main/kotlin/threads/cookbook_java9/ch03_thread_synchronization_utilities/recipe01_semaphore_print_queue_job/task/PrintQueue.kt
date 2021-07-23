package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe01_semaphore_print_queue_job.task

import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class PrintQueue {
    private val semaphore = Semaphore(3);
    private val freePrinters: Array<Boolean> = Array(3) {true}
    private val lockPrinters: Lock = ReentrantLock();

    fun printJob(document: Any) {
        try {
            semaphore.acquire();

            var assignedPrinter = getPrinter();

            var duration: Long = (Math.random() * 10).toLong();
            println("${Date()} - ${Thread.currentThread().name}: PrintQueue: Printing a Job in Printer $assignedPrinter during $duration seconds");

            TimeUnit.SECONDS.sleep(duration);

            // free the printer
            freePrinters[assignedPrinter] = true;
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } finally {
            semaphore.release();
        }
    }

    private fun getPrinter(): Int {
        var ret = -1
        try {
            // Get the access to the array
            lockPrinters.lock()

            // Look for the first free printer
            run loop@{
                freePrinters.indices.forEach {i ->
                    if (freePrinters[i]) {
                        ret = i
                        freePrinters[i] = false
                        return@loop
                    }
                }
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Free the access to the array
            lockPrinters.unlock()
        }
        return ret
    }

}