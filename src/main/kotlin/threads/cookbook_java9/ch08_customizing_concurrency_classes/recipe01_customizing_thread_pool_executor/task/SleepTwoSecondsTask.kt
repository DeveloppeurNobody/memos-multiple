package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe01_customizing_thread_pool_executor.task

import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class SleepTwoSecondsTask : Callable<String> {
    override fun call(): String {
        TimeUnit.SECONDS.sleep(2);
        return Date().toString();
    }
}

