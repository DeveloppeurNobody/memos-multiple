package threads.cookbook_java9.ch02_basic_synchronization.recipe03_lock

import java.util.concurrent.locks.ReentrantLock
import kotlin.math.roundToLong

/**
 * This class simulates a print queue.
 */
class PrintQueue(var fairMode: Boolean = false) {
    private val queueLock = ReentrantLock(fairMode);

    /**
     * Method that prints the Job. The printing is divded in two phase two show
     * how the fairness attribute affects the election of the thread who has
     * the control of the lock
     *
     * @param document The document to print
     */
    fun printJob(document: Object) {
        // locking before to proceed
        queueLock.lock();
        try {
            var duration = (Math.random() * 10000).toLong();
            println("${Thread.currentThread().name}: PrintQueue: Printing a Job during ${duration / 1000}");
            Thread.sleep(duration);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        } finally {
            // /!\ always unlock when done !
            queueLock.unlock();
        }

        // locking before to proceed
        queueLock.lock();
        try {
            var duration: Long = (Math.random() * 10000).toLong();
            println("${Thread.currentThread().name}: PrintQueue: Printing a Job during ${duration / 1000} seconds");
            Thread.sleep(duration);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } finally {
            queueLock.unlock();
        }
    }

}