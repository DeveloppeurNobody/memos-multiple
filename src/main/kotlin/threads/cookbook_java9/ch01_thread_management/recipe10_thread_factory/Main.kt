package threads.cookbook_java9.ch01_thread_management.recipe10_thread_factory

import threads.cookbook_java9.ch01_thread_management.recipe10_thread_factory.factory.MyThreadFactory
import threads.cookbook_java9.ch01_thread_management.recipe10_thread_factory.task.Task
import java.util.stream.IntStream


/**
 * Main class of the example. Creates a Thread factory and creates ten Thread
 * objects using that factory
 */
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the factory
        val factory = MyThreadFactory("MyThreadFactory");

        // Creates a task
        val task = Task();
        var thread: Thread;

        // Creates and starts ten Thread objects
        (0 until 10).forEach {
            thread = factory.newThread(task);
            thread.start();
        }

        // Prints the statistics of the ThreadFactory to the console.
        println("""
            Factory status:
            ${factory.stats}
        """.trimIndent());
    }
}
