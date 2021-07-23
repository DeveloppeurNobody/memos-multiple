package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe04_using_our_thread_factory_in_an_executor_object.core

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe04_using_our_thread_factory_in_an_executor_object.task.MyTask
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe04_using_our_thread_factory_in_an_executor_object.task.MyThreadFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new ThreadFactory object
        val threadFactory = MyThreadFactory("MyThreadFactory");

        // Create a new ThreadPoolExecutor and configure it for use the
        // MyThreadFactoryObject created before as the factory to create the threads
        val executor = Executors.newCachedThreadPool(threadFactory);

        // Create a new Task Object
        val task = MyTask();

        // Submit the task
        executor.submit(task);

        // Shutdown the executor
        executor.shutdown();

        // Wait for the finalization of the executor
        executor.awaitTermination(1, TimeUnit.DAYS);

        // Write a message indicating the of the program
        println("Main: End of the program.");
    }
}