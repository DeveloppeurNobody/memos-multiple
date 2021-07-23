package threads.cookbook_java9.ch04_thread_executor.recipe02_executing_tasks_in_executor_that_returns_a_result

import threads.cookbook_java9.ch04_thread_executor.recipe02_executing_tasks_in_executor_that_returns_a_result.task.FactorialCalculator
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
import kotlin.random.Random

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a ThreadPoolExecutor with fixed size. It has a maximum of two threads
        val executor = Executors.newFixedThreadPool(2) as ThreadPoolExecutor;

        // List to store the Future objects that control the execution of the task
        // and are used to obtain the results
        val resultList = mutableListOf<Future<Int>>();

        // Create a random number generator
        var random = java.util.Random();
        repeat(10) {
            val number = random.nextInt(10);
            val calculator = FactorialCalculator(number);
            val result: Future<Int> = executor.submit(calculator);
            resultList.add(result);
        }

        // Wait for the finalization of the ten tasks
        do {
            println("Main: Number of Completed Tasks: ${executor.completedTaskCount}");

            resultList.forEachIndexed { index, future ->
                println("Main: Task $index: ${future.isDone}");
            }

            try {
                Thread.sleep(50);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        } while (executor.completedTaskCount < resultList.size);

        // Write the results
        println("Main: Results");
        resultList.forEachIndexed { index, future ->
            var number: Int? = null;

            try {
                number = future.get()
            } catch (ie: Exception) {
              System.err.println(ie);
            }
            println("Core: Task $index: $number");
        }
        executor.shutdown();
    }
}

