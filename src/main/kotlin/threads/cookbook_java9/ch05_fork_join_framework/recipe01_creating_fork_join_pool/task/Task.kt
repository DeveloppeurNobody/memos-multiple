package threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.task

import threads.cookbook_java9.ch05_fork_join_framework.recipe01_creating_fork_join_pool.util.Product
import java.util.concurrent.ForkJoinTask
import java.util.concurrent.RecursiveAction
import java.util.concurrent.TimeUnit

/**
 * This class implements the tasks that are going to update the price information.
 * If the assigned interval of values is less that 10, it increases the prices of
 * the assigned products. In other case, it divides the assigned interval in two,
 * creates two new tasks and execute them
 *
 * Constructor of the class. Initializes its attributes
 * @param products list of products
 * @param first first element of the list assigned to the task
 * @param last last element of the list assigned to the task
 * @param increment price increment that this task has to apply
 */

class Task(val products: List<Product>,
           val first: Int,
           val last: Int,
           val increment: Double) : RecursiveAction() {


    // private val first: Int;
    // private val last: Int;
    companion object {
        private const val serialVersionUID = 1L;
    }


    /**
     * method that implements the job of the task
     */
    override fun compute() {
        if ((last - first) < 10) {
            updatePrices();
        } else {
            var middle = (last + first) / 2;
            println("Task: Pending tasks: ${ForkJoinTask.getQueuedTaskCount()}");
            val t1 = Task(products, first, middle+1, increment);
            val t2 = Task(products, middle+1, last, increment);
            ForkJoinTask.invokeAll(t1, t2);
        }
    }

    /**
     * method that updates the prices of the assigned products to the task
     */
    private fun updatePrices() {
        (first until last).forEach {
            val product = products[it];
            product.price = (product.price * (1 * increment));
        }
    }
}

