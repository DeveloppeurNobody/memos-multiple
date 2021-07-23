package threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.main

import threads.cookbook_java9.ch01_thread_management.recipe08_thread_local.task.SafeTask
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream
import java.util.stream.Stream

object SafeMain {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates a task
        val task = SafeTask();

        // Creates and start three Thread objects for that task
        (0 until 10).forEach { _ ->
            val thread = Thread(task);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }

            thread.start();
        }
    }
}