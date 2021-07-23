package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe04_using_our_thread_factory_in_an_executor_object.task

import java.util.concurrent.ThreadFactory

class MyThreadFactory(val prefix: String) : ThreadFactory {
    var counter = 1;

    override fun newThread(r: Runnable): Thread {
        val myThread = MyThread(r, "${prefix}-${counter}");
        counter++;
        return myThread;
    }
}