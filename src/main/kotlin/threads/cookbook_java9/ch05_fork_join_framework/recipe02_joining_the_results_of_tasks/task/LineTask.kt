package threads.cookbook_java9.ch05_fork_join_framework.recipe02_joining_the_results_of_tasks.task

import java.util.concurrent.ExecutionException
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveTask
import java.util.concurrent.TimeUnit

/**
 * Task that will process a fragment of a line of the document.
 * If the fragment is too big (100 words or more), it split it in
 * two parts and throw to tasks to process each of the fragments
 *
 * It returns the number of appearances of the word in the fragment
 * it has to process
 *
 * Constructor of the class
 * @param line A line of the document
 * @param start Position of the line where the task starts its process
 * @param end Position of the line where the task starts its process
 * @param word Work we are looking for
*/
class LineTask(
    val line: Array<String?>,
    val start: Int,
    val end: Int,
    val word: String) : RecursiveTask<Int>() {

    companion object {
        private const val serialVersionUID = 1L;
    }

    /**
     * If the part of the line it has to process is smaller that 100, it
     * calculates the number of appearances of the word in the block. Else,
     * it divides the block in two blocks and throws to LineTask to calculate
     * the number of appearances.
     */
    override fun compute(): Int {
        var result: Int = -1;
        if ((end - start) < 100) {
            result = count(line, start, end, word);
        } else {
            var mid = (start + end) / 2;
            val task1 = LineTask(line, start, mid, word);
            val task2 = LineTask(line, mid, end, word);
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
     * Groups the results of two LineTasks
     * @param number1 The result of the first LineTask
     * @param number2 The result of the second LineTask
     * @return The sum of the numbers
     */
    private fun groupResults(number1: Int, number2: Int): Int {
        return number1 + number2;
    }

    /**
     * Count the appearances of a word in a part of a line of a document
     * @param line A line of the document
     * @param start Position of the line where the method begin to count
     * @param end Position of the line where the method finish the count
     * @param word Word the method looks for
     * @return The number of appearances of the word in the part of the line
     */
    private fun count(line: Array<String?>, start: Int, end: Int, word: String): Int {
        var counter = 0;
        line.filter { it == word }
            .forEach { counter++; }

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        return counter;
    }
}
