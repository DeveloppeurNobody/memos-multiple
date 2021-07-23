package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task.MyScheduledTask
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task.MyScheduledThreadPoolExecutor
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task.Task
import java.util.*
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a MyScheduledThreadPool object
        val executor = MyScheduledThreadPoolExecutor(4);

        // Create a task object
        var task = Task();

        println("Main: ${Date()}");

        // Send to the executor a delayed task. It will be executed after 1 second of delay
        executor.schedule(task, 1, TimeUnit.SECONDS);

        // Sleeps the thread three seconds
        TimeUnit.SECONDS.sleep(3);

        // Create another task
        task = Task();

        println("Main: ${Date()}");

        // Send to the executor a delayed task. It will begin its execution after 1 second
        // of delay and then it will be executed every three seconds
        executor.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);

        // Sleep the thread during ten seconds
        TimeUnit.SECONDS.sleep(10);

        // Shutdown the executor
        executor.shutdown();

        // Wait for the finalization of the executor
        executor.awaitTermination(1, TimeUnit.DAYS);

        // Write a message indicating the of the program.
        println("Main: End of program");


    }
}