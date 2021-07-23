package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task

import java.util.concurrent.TimeUnit

class Task : Runnable {
    override fun run() {
        println("Task: Begin.\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println("Task: End.\n");
    }
}