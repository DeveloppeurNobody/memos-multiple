package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads.task

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