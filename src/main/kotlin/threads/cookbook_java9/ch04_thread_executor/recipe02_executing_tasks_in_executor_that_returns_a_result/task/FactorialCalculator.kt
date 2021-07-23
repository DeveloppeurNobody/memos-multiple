package threads.cookbook_java9.ch04_thread_executor.recipe02_executing_tasks_in_executor_that_returns_a_result.task

import java.util.concurrent.Callable


/**
 * This class calculates the factorial of a number. It can be executed
 * in an executor because it implements Callable interface.
 * The call method() will return an Integer
 */
class FactorialCalculator(val number: Int) : Callable<Int> {

    @Throws(Exception::class)
    override fun call(): Int {
        var num = number;
        var result = 1;

        // If the number is 0 or 1, return the 1 value
        if ((num == 0) || (num == 1)) {
            result = 1;
        } else {
            // Calculate the factorial
            (2..number).forEach {i ->
                result *= i;
                Thread.sleep(20);
            }
        }

        println("${Thread.currentThread().name}: $result");
        // Return the value
        return result;
    }
}