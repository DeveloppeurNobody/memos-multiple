package threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks.task

import java.util.concurrent.ExecutionException
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveTask

/**
 * Task that will process part of the document and calculate the number of
 * appearances of the word in that block. If it has to process
 * more that 10 lines, it divides its part in two and throws two DocumentTask
 * to calculate the number of appearances in each block.
 * In other case, it throws LineTasks to process the lines of the block
 *
 *
 * Constructor of the class
 * @param document Document to process
 * @param start Starting position of the block of the document this task has to process
 * @param end End position of the block of the document this task has to process
 * @param word Word we are looking for
*/
class DocumentTask(
    val document: Array<Array<String?>>,
    val start: Int,
    val end: Int,
    val word: String) : RecursiveTask<Int>() {

    companion object {
        private const val serialVersionUID = 1L;
    }

    /**
     * If the task has to process more that ten lines, it divide
     * the block of lines it two subblocks and throws two DocumentTask
     * two process them.
     * In other case, it throws LineTask tasks to process each line of its block
     */
    override fun compute(): Int {
        var result: Int = -1;
        if ((end - start) < 10) {
            result = processLines(document, start, end, word);
        } else {
            val mid = (start + end) / 2;
            val task1 = DocumentTask(document, start, mid, word);
            val task2 = DocumentTask(document, mid, end, word);
            ForkJoinTask.invokeAll(task1, task2);

            try {
                result = groupResults(task1.get(), task2.get());
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } catch (ee: ExecutionException) {
                System.err.println(ee);
            }
        }
        return result;
    }

    /**
     * Throws a LineTask task for each line of the block of lines this task has to process
     * @param document Document to process
     * @param start Starting position of the block of lines it has to process
     * @param end Finish position of the block of lines it has to process
     * @param word Word we are looking for
     * @return
     */
    private fun processLines(document: Array<Array<String?>>, start: Int, end: Int, word: String): Int {
        val tasks = mutableListOf<LineTask>();

        (start until end).forEach {
            val task = LineTask(document[it], 0, document[it].size, word);
            tasks.add(task);
        }
        ForkJoinTask.invokeAll(tasks);

        var result = 0;
        tasks.forEach {
            try {
                result += it.get();
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } catch (ee: ExecutionException) {
                System.err.println(ee);
            }
        }

        return result;
    }

    /**
     * Method that group the results of two DocumentTask tasks
     * @param number1 Result of the first DocumentTask
     * @param number2 Result of the second DocumentTask
     * @return The sum of the two results
     */
    private fun groupResults(number1: Int, number2: Int): Int {
        return number1 + number2
    }
}

