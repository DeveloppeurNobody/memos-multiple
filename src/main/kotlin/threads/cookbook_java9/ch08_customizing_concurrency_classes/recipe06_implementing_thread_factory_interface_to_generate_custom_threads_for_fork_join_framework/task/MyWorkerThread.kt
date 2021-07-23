package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.task

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ForkJoinWorkerThread

/**
 * This class implements a custom thread for the Fork/Join framework. It extends the
 * ForkJoinWorkerThread that is the default implementation of the threads that executes
 * the tasks in the Fork/Join Framework. This custom thread counts the number of tasks
 * executed in it
 *
 */
class MyWorkerThread(pool: ForkJoinPool) : ForkJoinWorkerThread(pool) {

    companion object {
        val taskCounter = ThreadLocal<Int>();
    }

    override fun onStart() {
        super.onStart();
        println("MyWorkerThread $id: Initializing task counter.");
        taskCounter.set(0);
    }

    override fun onTermination(exception: Throwable?) {
        println("MyWorkerThread: $id ${taskCounter.get()}");
        super.onTermination(exception);
    }

    fun addTask() {
        taskCounter.set(taskCounter.get() + 1);
    }
}