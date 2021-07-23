package threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.task

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * class that shows the problem generate when some Thread objects share a data
 * structure
 */
class UnsafeTask : Runnable {
    /**
     * Date shared by all threads
     */
    private lateinit var startDate: Date;

    override fun run() {
        startDate = Date();
        println("Starting Thread: ${Thread.currentThread().id} $startDate");

        try {
            TimeUnit.SECONDS.sleep((Math.random() * 10).toLong());
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        println("Thread Finished: ${Thread.currentThread().id} $startDate");
    }
}