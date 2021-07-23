package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe02_implementing_priority_based_executor

import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun main() {
    val executor = ThreadPoolExecutor(4, 4, 1,
        TimeUnit.SECONDS, PriorityBlockingQueue<Runnable>());

    repeat(10) {
        val task = MyPriorityTask("Task $it", it);
        executor.execute(task);
    }

    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    (10 until 20).forEach {
        val task = MyPriorityTask("Task $it", it);
        executor.execute(task);
    }

    executor.shutdown();

    try {
        executor.awaitTermination(1, TimeUnit.DAYS);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    println("Main: End of program");
}