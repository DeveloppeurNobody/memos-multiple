package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe07_customizing_tasks_running_in_fork_join_framework.task

import java.util.*
import java.util.concurrent.ForkJoinTask

/**
 * This class extends the ForkJoinTask class to implement your own version of a task running
 * in a ForkJoinPool of the Frok/Join framework. It's equivalent to the RecursiveAction and
 * Recursive classes. As the RecursiveAction class, it doesn't return any result
 *
 */
abstract class MyWorkerTask(val name: String) : ForkJoinTask<Void>() {
    companion object {
        const val serialVersionUID = 1L;
    }

    override fun getRawResult(): Void? {
        return null;
    }


    override fun exec(): Boolean {
        val startDate = Date();
        compute();
        val finishDate = Date();
        val diff = finishDate.time - startDate.time;
        println("MyWorkerTask: $name : $diff Milliseconds to complete.");
        return true;
    }

    protected abstract fun compute();

}