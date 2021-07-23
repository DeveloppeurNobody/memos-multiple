package threads.cookbook_java9.ch01_thread_management.recipe03_controlling_interrupts_thread_by_exception

import threads.cookbook_java9.ch01_thread_management.recipe03_controlling_interrupts_thread_by_exception.task.FileSearch
import java.util.concurrent.TimeUnit

object Main {

    /**
     * Main method of the core. Search for the autoexec.bat file on the root folder
     * and its subfolders during ten seconds and then, interrupts the Thread.
     */
    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // Create the runnable Object and the Thread to run it
        var searcher =
            FileSearch(
                "/home",
                "autoexec.bat"
            );
        val thread = Thread(searcher);

        println("Looking for autoexec.bat file...")
        // Starts the thread
        thread.start();

        // Wait ten seconds
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (e: java.lang.Exception) {
            System.err.println(e);
        }

        // Interrupts the thread
        thread.interrupt();
    }
}