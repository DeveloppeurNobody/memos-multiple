package threads.cookbook_java9.ch04_thread_executor.recipe08_controlling_a_finishing_task_in_executor

import java.util.concurrent.FutureTask

/**
 * This class manage the execution of a ExecutableTasks.
 * Override the done() method that is called when the task finish its execution
 */
class ResultTask(callable: ExecutableTask) : FutureTask<String>(callable) {
    /**
     * Name of the ResultTask. It's initialized with the name
     * of ExecutableTask that manages
     */
    private val name = callable.name;

    /**
     * Method that is called when the task finish
     */
    override fun done() {
        if (isCancelled) {
            println("$name: Has been cancelled");
        } else {
            println("$name: Has finished");
        }
    }
}