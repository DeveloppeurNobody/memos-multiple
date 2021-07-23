package threads.cookbook_java9.ch01_thread_management.recipe03_controlling_interrupts_thread_by_exception.task

import java.io.File
import java.lang.Exception

/**
 * this class search for files with a name in a directory
 */
class FileSearch(var initPath: String,
                 var filename: String) : Runnable {
    /**
     * Initial path for the search
     */
    //var initPath: String = "";

    /**
     * Name of the file we are searching for
     */
    //var filename: String = "";

    override fun run() {
        var file = File(initPath);
        if (file.isDirectory) {
            try {
                directoryProcess(file);
            } catch (e: Exception) {
                System.err.println(e);
                println("${Thread.currentThread().name}: The search has been interrupted");
                cleanResources();
            }
        }
    }

    /**
     * method for cleaning resources, in this case is empty
     */
    private fun cleanResources() {

    }

    /**
     * method that process a directory
     * @param file: Directory to process
     * @throws InterruptedException: if the thread is interrupted
     */
    @Throws(InterruptedException::class)
    private fun directoryProcess(file: File) {
        // Get the content of the directory
        val list = file.listFiles();
        list.indices.forEach {
            if (list[it].isDirectory) {
                // if is a directory, process it
                directoryProcess(list[it]);
            } else {
                // if is a file, process it
                fileProcess(list[it]);
            }
        }

        // Check the interruption
        if (Thread.interrupted()) {
            throw InterruptedException();
        }
    }

    /**
     * method that process a file
     */
    @Throws(InterruptedException::class)
    private fun fileProcess(file: File) {
        // check the name
        if (file.name.equals(filename)) {
            println("${Thread.currentThread().name} : ${file.absolutePath}");
        }

        // Check the interruption
        if (Thread.interrupted()) {
            throw InterruptedException();
        }
    }
}

