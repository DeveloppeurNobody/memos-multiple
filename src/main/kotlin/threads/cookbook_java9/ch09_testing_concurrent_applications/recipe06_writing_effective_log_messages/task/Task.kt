package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.task

import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe06_writing_effective_log_messages.logger.MyLoggerFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * This class is the Task you're going to use to test the
 * Logger system you have implemented. It simply write a log
 * message indicating the start of the task, sleeps the thread for
 * two seconds and write another log message indicating the end of the
 * task.
 *
 */
class Task : Runnable {
    override fun run() {
        // Get the logger
        val logger: Logger = MyLoggerFactory.getLogger(this.javaClass.name);

        // Write a message indicating the start of the task
        logger.entering(Thread.currentThread().name, "run()");

        // Sleep the task for two seconds
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Write a message indicating the end of the task
        logger.exiting(Thread.currentThread().name, "run()");
    }
}