package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe03_implementing_the_threadfactory_interface_to_generate_custom_threads.task

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class MyThreadFactory(val prefix: String) : ThreadFactory {
    val counter = AtomicInteger(1);

    override fun newThread(r: Runnable): Thread {
        val myThread = MyThread(r, "${prefix}-${counter.getAndIncrement()}");
        return myThread;
    }
}