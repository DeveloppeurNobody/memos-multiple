package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task

import java.util.*
import java.util.concurrent.*

/**
 *
 * This class implements an scheduled task to be execute in a scheduled thread pool executor. It's
 * a parameterized class where V is the type of data that will be returned by the task.
 *
 * An scheduled thread pool executor can execute two kinds of tasks:
 * 		Delayed Tasks: This kind of tasks are executed once after a period of time.
 * 		Periodic Tasks: This kind of tasks are executed from time to time
 * @param <V> Type of data that will be returned by the task
 *
 *
 * Constructor of the class. It initializes the attributes of the class
 * @param runnable Runnable submitted to be executed by the task
 * @param result Result that will be returned by the task
 * @param task Task that will execute the Runnable object
 * @param executor Executor that is going to execute the task
*/
class MyScheduledTask<V>(runnable: Runnable,
                         result: V?,
                         val task: RunnableScheduledFuture<V>,
                         val executor: ScheduledThreadPoolExecutor
) : FutureTask<V>(runnable, result), RunnableScheduledFuture<V> {

    var period = 0L;
    var startDate = 0L;

    override fun compareTo(other: Delayed?): Int = task.compareTo(other);


    override fun isPeriodic(): Boolean = task.isPeriodic;

    override fun getDelay(unit: TimeUnit): Long {
        return if (isPeriodic) {
            task.getDelay(unit);
        } else {
            if (startDate == 0L) {
                task.getDelay(unit);
            } else {
                val now = Date();
                val delay = startDate - now.time;
                unit.convert(delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    override fun run() {
        if (isPeriodic && (!executor.isShutdown)) {
            val now = Date();
            startDate = now.time + period;
            executor.queue.add(this);
        }
        println("""
            Pre-MyScheduledTask: ${Date()}
            MyScheduledTask: Is Periodic: $isPeriodic
        """.trimIndent());
        super.runAndReset();
        println("Post-MyScheduledTask: ${Date()}");
    }
}