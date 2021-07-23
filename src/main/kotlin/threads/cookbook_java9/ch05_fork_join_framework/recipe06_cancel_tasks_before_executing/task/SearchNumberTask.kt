package threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.task

import threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.util.TaskManager
import java.util.concurrent.RecursiveTask
import java.util.concurrent.TimeUnit

/**
 * This task look for a number in an array of integer numbers.
 * If the part of the array it has to process has more than
 * 10 elements, it creates two subtasks and executes then asynchronously
 * with the fork method. Otherwise, look for the number in the block
 * it has to process.
 *
 * If the task found the number, return the position where the number has
 * been found. Else, return the -1 value. If a subtask found the number,
 * the tasks suspend the other subtask and return the position where the number
 * has been found. If none of the two subtasks found the number, return the -1
 * value.
 *
 *
 * Constructor of the class
 * @param array Array of numbers
 * @param start Start position of the block of numbers this task has to process
 * @param end End position of the block of numbers this task has to process
 * @param number Number this task is going to look for
 * @param manager
*/
class SearchNumberTask(
    val numbers: Array<Int?>,
    val start: Int,
    val end: Int,
    val number: Int,
    val manager: TaskManager) : RecursiveTask<Int>() {

    companion object {
        private const val serialVersionUID = 1L;
        private const val NOT_FOUND = -1;
    }

    /**
     * If the block of number this task has to process has more than
     * ten elements, divide that block in two parts and create two
     * new Tasks using the launchTasks() method.
     * Else, looks for the number in the block assigned to it using
     * the lookForNumber() method
     */
    override fun compute(): Int {
        println("Task: $start: $end");
        return if ((end - start) > 0) {
            launchTasks();
        } else {
            lookForNumber();
        }
    }

    /**
     * Looks for the number in the block of numbers assigned to this task
     * @return The position where it found the number or -1 if it doesn't find it
     */
    private fun lookForNumber(): Int {
        for (i in start until end) {
            if (numbers[i] === number) {
                System.out.printf("Task: Number %d found in position %d\n", number, i)
                manager.cancelTasks(this)
                return i
            }
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        return NOT_FOUND
    }


    /**
     * Divide the block of numbers assigned to this task in two and
     * execute to new Task objects to process that blocks
     * @return The position where the number has been found of -1
     * if the number haven't been found in the subtasks
     */
    private fun launchTasks(): Int {
        val mid = (start + end) / 2;
        val task1 = SearchNumberTask(numbers, start, mid, number, manager);
        val task2 = SearchNumberTask(numbers, mid, end, number, manager);

        manager.addTask(task1);
        manager.addTask(task2);

        task1.fork();
        task2.fork();
        var returnValue: Int;

        returnValue = task1.join();
        if (returnValue != -1) {
            return returnValue;
        }

        returnValue = task2.join();
        return returnValue;
    }

    fun logCancelMessage() {
        println("Task: Cancelled task frim $start to $end");
    }
}

