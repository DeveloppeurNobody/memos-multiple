package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.core

import org.apache.commons.logging.Log
import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.logger.MyLoggerFactory
import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.task.Task;
import java.util.logging.Level

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Get the Logger object
        val logger = MyLoggerFactory.getLogger(Main.javaClass.name);

        // Write a message indicating the start of the execution
        logger.entering(Main.javaClass.name, "main()", args);

        // Create and launch five Task Objects
        val threads = arrayOfNulls<Thread>(5);
        threads.indices
            .forEach {
                logger.log(Level.INFO, "Launching thread: $it");
                val task = Task();
                threads[it] = Thread(task);
                logger.log(Level.INFO, "Thread created: ${threads[it]?.name}")
                threads[it]?.start();
            }

        // Write a log message indicating that the threads have been created
        logger.log(Level.INFO, "Ten threads created. Waiting for its finalization");

        // Wait for the finalization of the threads
        threads.forEach {
            try {
                it?.join();
                logger.log(Level.INFO, "Thread has finished its execution $it");
            } catch (ie: InterruptedException) {
                System.err.println(ie);
                logger.log(Level.SEVERE, "Exception", ie);
            }
        }

        logger.exiting(Main.javaClass.name, "main()");
    }
}