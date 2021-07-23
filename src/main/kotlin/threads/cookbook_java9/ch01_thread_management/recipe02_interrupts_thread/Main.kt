package threads.cookbook_java9.ch01_thread_management.recipe02_interrupts_thread

import threads.cookbook_java9.ch01_thread_management.recipe02_interrupts_thread.task.PrimeGenerator
import java.lang.Exception
import java.util.concurrent.TimeUnit

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Launch the prime numbers generator
        val task =
            PrimeGenerator();
        task.start();

        // Wait 5 seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (e: Exception) {
            System.err.println(e);
        }

        // Interrupt the prime number generator
        task.interrupt();

        // write information about the status of the Thread
        println("""
            Main: Status of the Thread: ${task.state}
            Main: IsInterrupted: ${task.isInterrupted}
            Main: isAlive: ${task.isAlive}
        """.trimIndent());
    }
}