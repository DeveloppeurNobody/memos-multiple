package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe04_using_our_thread_factory_in_an_executor_object.task

import java.util.concurrent.TimeUnit

class MyTask : Runnable {
    override fun run() {
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }
}