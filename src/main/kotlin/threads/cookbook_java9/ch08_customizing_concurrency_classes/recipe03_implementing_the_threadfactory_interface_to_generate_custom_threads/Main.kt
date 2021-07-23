package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads.task.MyTask
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads.task.MyThreadFactory

fun main() {
    // Create a Factory
    val myFactory = MyThreadFactory("MyThreadFactory");

    // Create a Task
    val myTask = MyTask();

    // Create a Thread using the Factory to execute the Task
    val thread = myFactory.newThread(myTask);

    // Start the thread
    thread.start();

    // Wait for the finalization of the thread
    thread.join();

    // Write the thread info to the console
    println("""
        Main: Thread information.
        $thread
        Main: End of the example.
    """.trimIndent())
}