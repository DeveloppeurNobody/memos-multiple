package threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.core

import threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.task.Task
import threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.util.Product
import threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.util.ProductListGenerator
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a list of products
        val generator = ProductListGenerator();
        val products = generator.generate(10000);

        // Create a task
        val task = Task(products, 0, products.size, 0.20);

        // Create a ForkJoinPool
        val pool: ForkJoinPool = ForkJoinPool();

        // Execute the Task
        pool.execute(task);

        // Write information about the pool
        do {
            println("""
                *******************************************************
                Main: Parallelism: ${pool.parallelism}
                Main: Active Threads: ${pool.activeThreadCount}
                Main: Task Count: ${pool.queuedTaskCount}
                Main: Steal Count: ${pool.stealCount}
                *******************************************************
            """.trimIndent());

            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        } while (!task.isDone);

        // Shutdown the pool
        pool.shutdown();

        // Check if the task has completed normally
        if (task.isCompletedNormally) {
            println("Main: The process has completed normally.");
        }

        // Expected result: 12. Write products which price is not 12
        products.filter { it.price != 12.0 }
            .forEach { println("Product ${it.name}: ${it.price}")  }

        // End of program
        println("Main: End of the program.");

    }
}