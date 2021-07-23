package threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.task

import java.util.*
import java.util.concurrent.TimeUnit

class SafeTask : Runnable {
    companion object {
        private val startDate: ThreadLocal<Date?> = object : ThreadLocal<Date?>() {
            override fun initialValue(): Date? {
                return Date()
            }
        }
    }

    override fun run() {
        // Writes the start date
        println("Starting Thread: ${Thread.currentThread().id} ${startDate.get()}");
        try {
            TimeUnit.SECONDS.sleep((Math.random() * 10).toLong());
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Writes the start date
        println("Thread Finished: ${Thread.currentThread().id} ${startDate.get()}");
    }
}