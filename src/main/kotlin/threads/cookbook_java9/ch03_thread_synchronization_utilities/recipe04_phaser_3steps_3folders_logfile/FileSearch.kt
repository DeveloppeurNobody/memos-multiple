package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe04_phaser_3steps_3folders_logfile

import java.io.File
import java.util.*
import java.util.concurrent.Phaser
import java.util.concurrent.TimeUnit

/**
 * This class search for files with an extenstion in a directory
 */
class FileSearch(val initPath: String,
                 val fileExtension: String,
                 val phaser: Phaser) : Runnable{

    /**
     * Phaser to control the execution of the FileSearch objects.
     * Their execution will be divided in three phases.
     * 1st: Look in the foolder and its subfolders for the files with the extension
     * 2nd: Filter the results. We only want the files modified today.
     * 3rd: Print the results
     */
    // val phaser: Phaser;

    var results = mutableListOf<String>();


    override fun run() {
        // Waits for the creation of all the FileSearch objects.

        println("${Thread.currentThread().name}: Starting.");

        // 1st Phase: Look for the files
        val file: File = File(initPath);
        if (file.isDirectory) {
            directoryProcess(file);
        }

        // If no results, deregister in the phaser and ends
        if (!checkResults()) {
            return;
        }

        // 2nd phase: Filter the results
        filterResults();

        // If no results after the filter, deregister in the phaser and ends
        if (!checkResults()) {
            return;
        }

        // 3rd Phase: Show info
        showInfo();
        phaser.arriveAndDeregister();
        println("${Thread.currentThread().name}: Work completed.");
    }

    /**
     * this methods prints the final results of the search
     */
    private fun showInfo() {
        results.forEach {
            val file = File(it);
            println("${Thread.currentThread().name}: ${file.absolutePath}");
        }
        // Waits for the end of all the FileSearch threads that are registred
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * This method checks if there are results after the execution of a phase.
     * if there aren't results, deregister the thread of the phaser.
     *
     * @return true if there are results, false if not
     */
    private fun checkResults(): Boolean {
        if (results.isEmpty()) {
            println("""
                ${Thread.currentThread().name}: Phase ${phaser.phase}: 0 results.
                ${Thread.currentThread().name}: Phase ${phaser.phase}: End.
            """.trimIndent());
            // No Results. Phase is completed but no more work to do. Deregister
            // for the phaser
            phaser.arriveAndDeregister();
            return false;
        } else {
            // There are results. Phase is completed. Wait to continue with the next phase.
            println("${Thread.currentThread().name}: Phase ${phaser.phase}: ${results.size} results.");
            phaser.arriveAndAwaitAdvance();
            return true;
        }
    }

    private fun filterResults() {
        val newResults = mutableListOf<String>();
        val actualDate = Date().time;
        results.forEach {
            val file = File(it);
            val fileDate = file.lastModified();

            if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                newResults.add(it);
            }
        }
        results = newResults;
    }

    /**
     * Method that process a directory
     *
     * @param file : Directory to process
     */
    private fun directoryProcess(file: File) {
        // Get the content of directory
        val list = file.listFiles();
        list?.forEach {
            if (it.isDirectory) {
                directoryProcess(it);
            } else {
                // if is a file, process it
                fileProcess(it);
            }
        }
    }

    /**
     * method that process a file
     *
     * @param file : File to process
     */
    private fun fileProcess(file: File) {
        if (file.name.endsWith(fileExtension)) {
            results.add(file.absolutePath);
        }
    }
}