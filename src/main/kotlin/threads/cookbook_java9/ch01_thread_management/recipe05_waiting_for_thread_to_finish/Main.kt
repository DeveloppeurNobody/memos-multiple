package threads.cookbook_java9.ch01_thread_management.recipe05_waiting_for_thread_to_finish

import threads.cookbook_java9.ch01_thread_management.recipe05_waiting_for_thread_to_finish.task.DataSourcesLoader
import threads.cookbook_java9.ch01_thread_management.recipe05_waiting_for_thread_to_finish.task.NetworkConnectionsLoader
import java.time.Instant

/**
 * Main class of the example.
 * Create and start two initialization tasks
 * and wait for their finish
 */
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates and starts a DataSourceLoader runnable object
        val dsLoader =
            DataSourcesLoader();
        val thread1 = Thread(dsLoader, "DataSourceThread");

        // Creates and starts a NetworkConnectionsLoader runnable object
        val ncLoader =
            NetworkConnectionsLoader();
        val thread2 = Thread(ncLoader, "NetworkConnectionsLoader");

        // Start both threads
        thread1.start();
        thread2.start();

        try {
            println("waiting for DataSourceThread to finish...");
            thread1.join();
            println("DataSourceThread done!")

            // we wait until thread1 finished or for 1second
            // thread1.join(1000);

            println("wainting for NetworkConnectionsLoader to finish...")
            thread2.join();
            println("NetworkConnectionsLoader done!")
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        println("Main: Configuration has been loaded: ${Instant.now()}");
    }
}