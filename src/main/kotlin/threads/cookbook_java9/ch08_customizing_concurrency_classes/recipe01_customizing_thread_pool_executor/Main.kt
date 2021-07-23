package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe01_customizing_thread_pool_executor

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe01_customizing_thread_pool_executor.executor.MyExecutor
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe01_customizing_thread_pool_executor.task.SleepTwoSecondsTask
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

fun main() {
    val myExecutor = MyExecutor(4, 8, 1000, TimeUnit.MILLISECONDS, LinkedBlockingDeque<Runnable>());

    val results = mutableListOf<Future<String>>();

    repeat(10) {
        val task = SleepTwoSecondsTask();
        val result = myExecutor.submit(task);
        results.add(result);
    }

    repeat(5) {
        try {
            val result = results.get(it).get();
            println("Main: Result for Task $it : $result");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } catch (ee: ExecutionException) {
            System.err.println(ee);
        }
    }

    myExecutor.shutdown();

    (5 until 10).forEach {
        try {
            val result = results.get(it).get();
            println("Main: Result for Task $it : $result");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } catch (ee: ExecutionException) {
            System.err.println(ee)
        }
    }

    try {
        myExecutor.awaitTermination(1, TimeUnit.DAYS);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    println("Main: End of program.")
}