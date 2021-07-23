package threads.cookbook_java9.ch01_thread_management.recipe10_thread_factory.task

import java.util.concurrent.TimeUnit

class Task : Runnable {
    override fun run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }
}