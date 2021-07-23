package threads.cookbook_java9.ch02_basic_synchronization.recipe03_lock

import java.util.concurrent.TimeUnit

object Main {

    /**
     * Main method of the class. Run ten jobs in parallel that
     * sends documents to the print queue at the same time.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        println("Running example with fair-mode = false");
        testPrintQueue(false);
        println("Running example with fair-mode = true");
        testPrintQueue(true);
    }

    private fun testPrintQueue(fairMode: Boolean) {
        // Creates the print queue
        val printQueue = PrintQueue(fairMode);

        // Creates ten Threads
        val threads = arrayOfNulls<Thread>(10);
        threads.forEachIndexed { index, _ ->
            threads[index] = Thread(Job(printQueue), "Thread $index");
        }

        // Starts the threads
        (0 until 10).forEach {
            threads[it]?.start();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }

        // Wait for the end of the threads
        (0 until 10).forEach {
            try {
                threads[it]?.join();
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}