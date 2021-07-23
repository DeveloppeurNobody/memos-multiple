package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe05_customizing_tasks_running_in_a_scheduled_thread_pool.task

import java.util.concurrent.*

/**
 * Our implementation of an ScheduledThreadPoolExecutor two executes MyScheduledTasks tasks. It extends
 * the ScheduledThreadPoolExecutor class
 *
 */
class MyScheduledThreadPoolExecutor(corePoolSize: Int) : ScheduledThreadPoolExecutor(corePoolSize) {

    override fun <V : Any?> decorateTask(
        runnable: Runnable?,
        task: RunnableScheduledFuture<V>?
    ): RunnableScheduledFuture<V> {
        val myTask: MyScheduledTask<V> = MyScheduledTask<V>(runnable!!, null, task!!, this);
        return myTask;
    }

    override fun scheduleAtFixedRate(
        command: Runnable,
        initialDelay: Long,
        period: Long,
        unit: TimeUnit
    ): ScheduledFuture<*> {
        val task =  super.scheduleAtFixedRate(command, initialDelay, period, unit);
        val myTask: MyScheduledTask<*> = task as MyScheduledTask<*>;
        myTask.period = TimeUnit.MILLISECONDS.convert(period, unit);
        return task;
    }


}