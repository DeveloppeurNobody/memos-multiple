package threads.cookbook_java9.ch04_thread_executor.recipe05_running_task_in_executor_after_a_delay.task

import java.util.*
import java.util.concurrent.Callable

class Task(val name: String) : Callable<String> {

    /**
     * Main method of the task. Writes a message to the console
     * with the actual date and returns the "Hello world' string
     */
    override fun call(): String {
        println("$name: Starting ${Date()}");
        return "Hello, world";
    }
}

