package threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.main

import threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.task.UnsafeTask
import java.util.concurrent.TimeUnit

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the unsafe task
        val task = UnsafeTask();

        // Throw ten threads objects
        (0 until 10).forEach {
            Thread(task).start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}