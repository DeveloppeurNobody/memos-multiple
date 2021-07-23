package threads.cookbook_java9.ch04_thread_executor.recipe07_cancelling_task_in_executor

import java.util.concurrent.Callable

/**
 * This class implements the task of the example. It simply writes a message
 * to the console every 100 milliseconds
 */
class Task : Callable<String> {

    override fun call(): String {
        while (true) {
            println("Task: Test");
            Thread.sleep(100);
        }
    }
}