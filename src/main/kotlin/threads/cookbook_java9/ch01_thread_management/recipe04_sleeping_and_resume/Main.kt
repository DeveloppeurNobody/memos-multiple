package threads.cookbook_java9.ch01_thread_management.recipe04_sleeping_and_resume

import threads.cookbook_java9.ch01_thread_management.recipe04_sleeping_and_resume.task.ConsoleClock
import java.lang.Exception
import java.util.concurrent.TimeUnit

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates a FileClock runnable object and a thread
        // to run it
        val clock =
            ConsoleClock();
        val thread = Thread(clock);

        // Start the thread
        thread.start();
        try {
            // Wait for five seconds
            TimeUnit.SECONDS.sleep(5);
        } catch (e: Exception) {
            System.err.println(e);
        }

        // Interrupts the Thread
        thread.interrupt();
    }
}