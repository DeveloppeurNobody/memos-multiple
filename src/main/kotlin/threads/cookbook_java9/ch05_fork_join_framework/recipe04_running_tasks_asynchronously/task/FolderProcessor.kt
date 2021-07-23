package threads.cookbook_java9.ch05_fork_join_framework.recipe04_running_tasks_asynchronously.task

import java.io.File
import java.util.*
import java.util.concurrent.CountedCompleter


/**
 * Task that process a folder. Throw a new FolderProcesor task for each
 * subfolder. For each file in the folder, it checks if the file has the extension
 * it's looking for. If it's the case, it add the file name to the list of results.
 *
 * Constructor of the class
 * @param completer Completer of the task;
 * @param path Path of the folder this task is going to process
 * @param extension Extension of the files this task is looking for
 */
open class FolderProcessor : CountedCompleter<MutableList<String>> {
    private var path: String;
    private var extension: String;

    private val tasks: MutableList<FolderProcessor> = mutableListOf();
    private val resultList: MutableList<String> = mutableListOf();

    protected constructor(completer: CountedCompleter<*>, path: String, extension: String): super(completer) {
        this.path = path;
        this.extension = extension;
    }

    constructor(path: String, extension: String) {
        this.path = path;
        this.extension = extension;
    }

    companion object {
        private const val serialVersionUID = 1L;
    }


    /**
     * Main method of the task. It throws an additional FolderProcessor task
     * for each folder in this folder. For each file in the folder, it compare
     * its extension with the extension it's looking for. If they are equals, it
     * add the full path of the file to the list of results
     */
    override fun compute() {
        val file = File(path);
        val content = file.listFiles();

        // if (content != null)
        content?.forEach {
            if (it.isDirectory) {
                // If is a directory, process it. Execute a new Task.
                val task = FolderProcessor(this, it.absolutePath, extension);
                task.fork();
                addToPendingCount(1);
                tasks.add(task);
            } else {
                // If is a file, process it. Compare the extension of the file and the extension
                // it's looking for
                if (checkFile(it.name)) {
                    resultList.add(it.absolutePath);
                    println("Result found: ${it.absolutePath}");
                }
            }
        }

        // If the number of tasks thrown by this tasks is bigger than 50, we write a message
        if (tasks.size > 50) {
            println("${file.absolutePath}: ${tasks.size} tasks ran.");
        }

        // Try the completion of the task
        tryComplete();
    }

    override fun onCompletion(caller: CountedCompleter<*>?) {
        tasks.forEach { childTask -> resultList.addAll(childTask.resultList) }
    }

    /**
     * Checks if a name of a file has the extension the task is looking for
     * @param name name of the file
     * @return true if the name has the extension or false otherwise
     */
    private fun checkFile(name: String): Boolean = name.endsWith(extension);

    /**
     * Method that return the list of results
     * @return the resultList
     */
    fun getResultList(): MutableList<String> = resultList;
}
