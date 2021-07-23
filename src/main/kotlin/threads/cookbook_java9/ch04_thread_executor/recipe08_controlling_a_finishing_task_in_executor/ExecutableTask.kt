package threads.cookbook_java9.ch04_thread_executor.recipe08_controlling_a_finishing_task_in_executor

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * This class implements the task of this example.
 * It waits a random period of time
 */
class ExecutableTask(val name: String) : Callable<String> {
    override fun call(): String {
        try {
            val duration = (Math.random() * 10).toLong();
            println("$name: Waiting $duration seconds for results.");
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
        return "Hello, world. I'm $name";
    }
}