package threads.cookbook_java9.ch04_thread_executor.recipe04_run_multiple_tasks_and_proccess_all_tasks.task

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class Task(val name: String) : Callable<Result> {

    /**
     * Main method of the task. Waits during a random period of time
     * and then calculates the sum of five random numbers.
     */
    @Throws(Exception::class)
    override fun call(): Result {
        // Writes a message to the console
        println("$name: Starting");

        // Waits during a random period of time
        try {
            val duration = (Math.random() * 10).toLong();
            println("$name: Waiting $duration seconds for results.");
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        // Calculates the sum of five random numbers
        var value = 0;
        repeat(5) {
            value += (Math.random() * 10).toInt();
        }

        // Create the object with the results
        val result = Result();
        result.name = name;
        result.value = value;
        println("$name: Ends");

        // returns the result object
        return result;
    }
}

