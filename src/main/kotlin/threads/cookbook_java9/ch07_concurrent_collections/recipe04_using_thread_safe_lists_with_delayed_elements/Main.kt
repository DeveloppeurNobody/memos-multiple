package threads.cookbook_java9.ch07_concurrent_collections.recipe04_using_thread_safe_lists_with_delayed_elements

import threads.cookbook_java9.ch07_concurrent_collections.recipe04_using_thread_safe_lists_with_delayed_elements.task.Event
import threads.cookbook_java9.ch07_concurrent_collections.recipe04_using_thread_safe_lists_with_delayed_elements.task.Task
import java.util.*
import java.util.concurrent.DelayQueue
import java.util.concurrent.TimeUnit

object Main {
    /**
     * @param args
     */
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        /*
		 * Delayed queue to store the events
		 */
        val queue = DelayQueue<Event>()

        /*
		 * An array to store the Thread objects that execute the tasks
		 */
        val threads = arrayOfNulls<Thread>(5)

        /*
		 * Create the five tasks
		 */for (i in threads.indices) {
            val task = Task(i + 1, queue)
            threads[i] = Thread(task)
        }

        /*
		 * Execute the five tasks
		 */for (i in threads.indices) {
            threads[i]!!.start()
        }

        /*
		 * Wait for the finalization of the five tasks
		 */for (i in threads.indices) {
            threads[i]!!.join()
        }

        /*
		 * Write the results to the console
		 */
        do {
            var counter = 0
            var event: Event?
            do {
                event = queue.poll()
                if (event != null) counter++
            } while (event != null)
            System.out.printf("At %s you have read %d events\n", Date(), counter)
            TimeUnit.MILLISECONDS.sleep(500)
        } while (queue.size > 0)
    }
}
