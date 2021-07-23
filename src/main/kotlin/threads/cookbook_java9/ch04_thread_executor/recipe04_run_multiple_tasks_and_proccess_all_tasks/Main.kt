package threads.cookbook_java9.ch04_thread_executor.recipe04_run_multiple_tasks_and_proccess_all_tasks

import threads.cookbook_java9.ch04_thread_executor.recipe04_run_multiple_tasks_and_proccess_all_tasks.task.Result
import threads.cookbook_java9.ch04_thread_executor.recipe04_run_multiple_tasks_and_proccess_all_tasks.task.Task
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create an executor
        val executor = Executors.newCachedThreadPool();

        // Create three tasks and stores them in a list
        val taskList = mutableListOf<Task>();
        repeat(10) {
            val task = Task("Task-$it");
            taskList.add(task);
        }

        // Call the invokeAll() method
        var resultList: MutableList<Future<Result>> = mutableListOf();
        try {
            resultList = executor.invokeAll(taskList);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        // Finish the executor
        executor.shutdown();

        // Writes the results to the console
        println("Core: Printing the results.");


        resultList.forEach { future ->
            try {
                val result = future.get();
                println("${result.name}: ${result.value}")
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } catch (ee: ExecutionException) {
                System.err.println(ee)
            }
        }
    }
}