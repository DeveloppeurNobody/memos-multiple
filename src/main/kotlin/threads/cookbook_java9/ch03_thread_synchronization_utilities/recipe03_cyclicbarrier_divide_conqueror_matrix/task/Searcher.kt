package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.task

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils.MatrixMock
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils.Results
import java.util.concurrent.BrokenBarrierException
import java.util.concurrent.CyclicBarrier


/**
 * class that search for number in a set of rows of the bi-dimensional array
 */
class Searcher(private val firstRow: Int,
               private val lastRow: Int,
               private val mock: MatrixMock,
               private val results: Results,
               private val number: Int,
               private val barrier: CyclicBarrier
) : Runnable {
    /**
     * Main method of the searcher. Look for the number in a subset of rows. For each row, saves the
     * number of occurrences of the number in the array of results
     */

    override fun run() {
        var counter: Int;
        println("${Thread.currentThread().name}: Processing lines from $firstRow to $lastRow");
        (firstRow until lastRow).forEach { i ->
            var row = mock.getRow(i);
            counter = 0;
            row.filter { j -> j == number }
                .forEach { counter++ }
            results.setData(i, counter);
        }
        println("${Thread.currentThread().name}: Lines processed.")
        try {
            barrier.await();
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        } catch (bbe: BrokenBarrierException) {
            System.err.println(bbe);
        }
    }
}
