package threads.cookbook_java9.ch01_thread_management.recipe05_waiting_for_thread_to_finish.task

import java.time.Instant
import java.util.concurrent.TimeUnit

class DataSourcesLoader : Runnable {
    override fun run() {
        // Writes a message
        println("Begining data sources loading: ${Instant.now()}");

        // Sleeps four seconds
        try {
          TimeUnit.SECONDS.sleep(4);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Writes a message
        println("Data sources loading has finished: ${Instant.now()}");
    }
}