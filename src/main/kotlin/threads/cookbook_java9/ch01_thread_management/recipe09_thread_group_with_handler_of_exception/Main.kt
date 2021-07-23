package threads.cookbook_java9.ch01_thread_management.recipe09_thread_group_with_handler_of_exception

import threads.cookbook_java9.ch01_thread_management.recipe09_thread_group_with_handler_of_exception.group.MyThreadGroup
import threads.cookbook_java9.ch01_thread_management.recipe09_thread_group_with_handler_of_exception.task.Task

object Main {

    /**
     * Main method of the example. Creates a group of threads of MyThreadGroup
     * class and two threads inside this group
     */
    @JvmStatic
    fun main(args: Array<String>) {
        // calculate the number of threads
        val numberOfThreads = 2 * Runtime.getRuntime().availableProcessors();

        // Create a MyThreadGroup object
        val threadGroup = MyThreadGroup("MyThreadGroup");

        // Create a Tasks object;
        val task = Task();

        (0 until numberOfThreads).forEach {
            Thread(threadGroup, task).start();
        }

        println("Number of Threads: ${threadGroup.activeCount()}");
        println("Information about the Thread Group");
        threadGroup.list();

        // Write information about the status of the Thread objects to the console.
        val threads = arrayOfNulls<Thread>(threadGroup.activeCount());
        threadGroup.enumerate(threads);

        (0 until threadGroup.activeCount()).forEach {
            println("Thread ${threads[it]?.name} : ${threads[it]?.state}");
        }
    }
}