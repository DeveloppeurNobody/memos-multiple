package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe06_implementing_thread_factory_interface_to_generate_custom_threads_for_fork_join_framework.task

import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveTask
import java.util.concurrent.TimeUnit

class MyRecursiveTask(val array: Array<Int>,
                      val start: Int,
                      val end: Int) : RecursiveTask<Int>() {

    companion object {
        const val serialVersionUID = 1L;
    }

    override fun compute(): Int {
        var ret: Int = 0;
        val thread = Thread.currentThread() as MyWorkerThread;
        thread.addTask();
        if (end - start > 100) {
            val mid = (start + end) / 2;
            val task1 = MyRecursiveTask(array, start, mid);
            val task2 = MyRecursiveTask(array, mid, end);
            ForkJoinTask.invokeAll(task1, task2);
        } else {
            var add = 0;
            (start until end).forEach {
                add += array[it];
            }
            ret = add;
        }

        try {
          TimeUnit.MILLISECONDS.sleep(10);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        return ret;
    }

    fun addResults(task1: MyRecursiveTask, task2: MyRecursiveTask): Int {
        var value: Int;
        value = try {
            (task1.get().toInt()) + (task2.get().toInt());
        } catch (ie: InterruptedException) {
            System.err.println(ie);
            0;
        }
        return value;
    }
}