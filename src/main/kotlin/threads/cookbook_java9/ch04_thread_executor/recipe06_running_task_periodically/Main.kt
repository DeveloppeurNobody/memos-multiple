package threads.cookbook_java9.ch04_thread_executor.recipe06_running_task_periodically

import threads.cookbook_java9.ch04_thread_executor.recipe06_running_task_periodically.task.Task
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a ScheduledThreadPoolExecutor
        val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1);
        println("Main: Starting at: ${Date()}");

        // Create a new task and sends it to the executor. It will start with a delay
        // of 1 second and then it will execute every two seconds
        val task = Task("Task");
        val result: ScheduledFuture<*> = executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

        // Controlling the execution of tasks
        repeat(10) {
            println("Main: Delay; ${result.getDelay(TimeUnit.MILLISECONDS)}");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        }

        // Finish the executor
        executor.shutdown();
        println("Main: No more tasks at: ${Date()}");
        // Verify that the periodic tasks no is in execution after the executor shutdown()
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // The example finish
        println("Main: Finished at: ${Date()}");
    }
}