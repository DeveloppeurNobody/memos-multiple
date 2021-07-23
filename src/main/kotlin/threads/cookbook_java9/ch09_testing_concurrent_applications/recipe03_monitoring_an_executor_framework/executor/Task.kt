package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe03_monitoring_an_executor_framework.executor

import java.util.concurrent.TimeUnit

class Task (val milliseconds: Long) : Runnable {

    override fun run() {
        println("${Thread.currentThread().name}: Begin");
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println("${Thread.currentThread().name}: End\n");
    }
}

