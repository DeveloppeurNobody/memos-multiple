package threads.cookbook_java9.ch01_thread_management.recipe05_waiting_for_thread_to_finish.task

import java.lang.Exception
import java.time.Instant
import java.util.concurrent.TimeUnit

class NetworkConnectionsLoader : Runnable {

    override fun run() {
        // Writes a message
        println("Begining network connections loading: ${Instant.now()}");

        // Sleep six seconds
        try {
          TimeUnit.SECONDS.sleep(6);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Writes a message
        println("Network connections loading has finished ${Instant.now()}");
    }
}

