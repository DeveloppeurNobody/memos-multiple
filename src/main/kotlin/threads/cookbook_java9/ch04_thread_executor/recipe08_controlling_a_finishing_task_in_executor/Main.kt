package threads.cookbook_java9.ch04_thread_executor.recipe08_controlling_a_finishing_task_in_executor

import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Main class of the example. Creates five tasks that wait a random period of time.
 * Waits 5 seconds and cancel all the tasks. Then, write the results of that tasks
 * that haven't been cancelled.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create an executor
        val executor = Executors.newCachedThreadPool();

        // Create five tasks
        val resultTasks = arrayOfNulls<ResultTask>(5);
        repeat(5) {
            val executableTask = ExecutableTask("Task-$it");
            resultTasks[it] = ResultTask(executableTask);
            executor.submit(resultTasks[it]);
        }

        // Sleep the thread five seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Cancel all the tasks. In the tasks that have finished before this moment,
        // This cancellation has no effects
        resultTasks.forEach {
            it?.cancel(true);
        }

        // Write the results of those tasks that haven't been cancelled
        resultTasks.forEach {
            try {
                if (!it?.isCancelled!!) {
                    println("${it?.get()}")
                }
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } catch (ee: ExecutionException) {
                System.err.println(ee)
            }
        }

        // Finish the executor
        executor.shutdown();
    }
}