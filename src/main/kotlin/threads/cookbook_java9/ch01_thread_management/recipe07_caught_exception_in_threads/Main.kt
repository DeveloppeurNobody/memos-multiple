package threads.cookbook_java9.ch01_thread_management.recipe07_caught_exception_in_threads

import threads.cookbook_java9.ch01_thread_management.recipe07_caught_exception_in_threads.handler.ExceptionHandler
import threads.cookbook_java9.ch01_thread_management.recipe07_caught_exception_in_threads.task.Task

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the task
        val task =
            Task();

        // Creates the thread
        val thread = Thread(task);

        // Sets the UncaughtException Handler
        thread.uncaughtExceptionHandler =
            ExceptionHandler();

        // Starts the thread
        thread.start();

        try {
            thread.join()
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        println("Thread has finished");
    }
}