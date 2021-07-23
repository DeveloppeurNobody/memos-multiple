package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.task.Grouper
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.task.Searcher
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils.MatrixMock
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils.Results
import java.util.concurrent.CyclicBarrier


object Main {
    /**
     * Main method of the example
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {

        /*
		 * Initializes the bi-dimensional array of data
		 * 		10000 rows
		 * 		1000 numbers in each row
		 * 		Looking for number 5
		 */
        val ROWS = 10000
        val NUMBERS = 1000
        val SEARCH = 5
        val NB_OF_PARTICIPANTS = 5
        val LINES_PARTICIPANT = 2000
        val mock = MatrixMock(ROWS, NUMBERS, SEARCH)

        // Initializes the object for the results
        val results = Results(ROWS)

        // Creates an Grouper object
        val grouper = Grouper(results)

        // Creates the CyclicBarrier object. It has 5 participants and, when
        // they finish, the CyclicBarrier will execute the grouper object
        val barrier = CyclicBarrier(NB_OF_PARTICIPANTS, grouper)

        // Creates, initializes and starts 5 Searcher objects, with barrier reference
        val searchers = arrayOfNulls<Searcher>(NB_OF_PARTICIPANTS)
        for (i in 0 until NB_OF_PARTICIPANTS) {
            searchers[i] = Searcher(
                i * LINES_PARTICIPANT,
                i * LINES_PARTICIPANT + LINES_PARTICIPANT,
                mock,
                results,
                5,
                barrier
            )
            val thread = Thread(searchers[i])
            thread.start()
        }
        System.out.printf("Main: The main thread has finished.\n")
    }
}
