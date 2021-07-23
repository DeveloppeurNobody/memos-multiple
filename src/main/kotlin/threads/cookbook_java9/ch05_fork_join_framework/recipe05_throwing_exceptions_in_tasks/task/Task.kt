package threads.cookbook_java9.ch05_fork_join_framework.recipe05_throwing_exceptions_in_tasks.task

import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveTask
import java.util.concurrent.TimeUnit
import kotlin.RuntimeException


/**
 * This task throws and exception. It process an array of elements. If the
 * block of elements it has to process has 10 or more elements, it divides
 * the block in two and executes two subtasks to process those blocks. Else,
 * sleeps the task one second. Additionally,  If the block of elements it
 * has to process has the third position, it throws an exception.
 *
 *
 * Constructor of the class
 * @param array Array to process
 * @param start Start position of the block of elements this task has to process
 * @param end End position of the block of elements this task has to process
*/
class Task(
    val array: Array<Int?>,
    val start: Int,
    val end: Int) : RecursiveTask<Int>() {

    /**
     * Main method of the task. If the block of elements it has to process has 10
     *  or more elements, it divides the block in two and executes two subtasks
     *  to process those blocks. Else, sleeps the task one second. Additionally,
     *  If the block of elements it has to process has the third position, it
     *  throws an exception.
     */
    override fun compute(): Int {
        println("Task: Start from $start to $end");
        if ((end - start) < 10) {
            if ((3 > start) && (3 < end)) {
                throw RuntimeException("This task thorws an Exception: Task from $start to $end");
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        } else {
            val mid = (end + start) / 2;
            val task1 = Task(array, start, mid);
            val task2 = Task(array, mid, end);
            ForkJoinTask.invokeAll(task1, task2);
            println("Task: Result form $start to ${task1.join()}");
            println("Task: Result form $mid to $end ${task2.join()}");
        }
        println("Task: End form $start to $end");
        return 0;
    }
}