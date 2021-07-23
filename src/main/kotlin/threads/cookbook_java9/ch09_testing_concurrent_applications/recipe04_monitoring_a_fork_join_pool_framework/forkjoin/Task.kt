package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe04_monitoring_a_fork_join_pool_framework.forkjoin

import java.util.concurrent.RecursiveAction
import java.util.concurrent.TimeUnit

/**
 * This class implements a task used to show how to monitor the
 * behavior of a Fork/Join pool. The main objective of the task
 * is increment all the elements of an array. Every task has to
 * process a set of elements of this array. If the task has to process
 * more than 100 elements, it divides the set it has two process in two
 * subsets and create two tasks to execute them. Otherwise, it process
 * the elements of the subset it has to process
 *
 * Constructor of the class. Initializes its attributes
 * @param array Array of elements this task has to process
 * @param start Start position of the set of elements this task has to process
 * @param end End position of the set of elements this task has to process
*/
class Task (val array: Array<Int>,
            val start: Int,
            val end: Int) : RecursiveAction(){

    companion object {
        const val serialVersionUID = 1L;
    }


    override fun compute() {
        if ((end - start) > 100) {
            val mid = (start + end) / 2;
            val task1 = Task(array, start, mid);
            val task2 = Task(array, mid, end);

            // Start the sub-tasks
            task1.fork();
            task2.fork();

            // Wait for the finalization of the sub-tasks
            task1.join();
            task2.join();
        } else {
            (start until end).forEach {
                array[it]++;

                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (ie: InterruptedException) {
                    System.err.println(ie);
                }
            }
        }
    }
}

