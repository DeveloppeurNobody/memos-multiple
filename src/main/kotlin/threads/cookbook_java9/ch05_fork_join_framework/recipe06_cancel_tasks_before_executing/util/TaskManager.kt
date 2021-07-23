package threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.util

import threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.task.SearchNumberTask
import java.util.concurrent.ConcurrentLinkedDeque

/**
 * Class that stores all the tasks that have been sent to
 * a ForkJoinPool. Provides a method for the cancellation of
 * all the tasks
 *
 */
class TaskManager {
    val tasks: ConcurrentLinkedDeque<SearchNumberTask> = ConcurrentLinkedDeque();

    fun addTask(task: SearchNumberTask) {
        tasks.add(task);
    }

    fun cancelTasks(cancelTask: SearchNumberTask) {
        tasks.filter { it != cancelTask }
            .forEach {
                it.cancel(true);
                it.logCancelMessage();
            }
    }

}