package threads.cookbook_java9.ch04_thread_executor.recipe06_running_task_periodically.task

import java.util.*

/**
 * This class implements the task of the example. Writes a message to
 * the console with the actual date.
 *
 * It used to explain the utilization of an scheduled executor to
 * execute tasks periodically
 */
class Task(val name: String) : Runnable {
    override fun run() {
        println("$name: Executed at: ${Date()}");
    }
}

