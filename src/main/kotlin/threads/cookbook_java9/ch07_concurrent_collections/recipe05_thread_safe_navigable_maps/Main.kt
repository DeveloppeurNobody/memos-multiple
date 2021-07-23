package threads.cookbook_java9.ch07_concurrent_collections.recipe05_thread_safe_navigable_maps

import threads.cookbook_java9.ch07_concurrent_collections.recipe05_thread_safe_navigable_maps.task.Task
import threads.cookbook_java9.ch07_concurrent_collections.recipe05_thread_safe_navigable_maps.util.Contact
import java.util.concurrent.ConcurrentSkipListMap


object Main {

    /**
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        /*
		 * Create the navigable map
		 */
        val map = ConcurrentSkipListMap<String, Contact>()

        /*
		 * Create an array to store the 26 threads that execute the tasks
		 */
        val threads = arrayOfNulls<Thread>(26)
        var counter = 0

        /*
		 * Execute the 26 tasks
		 */
        var i = 'A'
        while (i <= 'Z') {
            val task = Task(map, i.toString())
            threads[counter] = Thread(task)
            threads[counter]!!.start()
            counter++
            i++
        }

        /*
		 * Wait for the finalization of the threads
		 */
        for (thread in threads) {
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        /*
		 * Write the size of the map
		 */
        System.out.printf("Main: Size of the map: %d\n", map.size)

        /*
		 * Write the first element of the map
		 */
        var element: Map.Entry<String, Contact>?
        var contact: Contact
        element = map.firstEntry()
        contact = element.value
        System.out.printf("Main: First Entry: %s: %s\n", contact.name, contact.phone)

        /*
		 * Write the last element of the map
		 */
        element = map.lastEntry()
        contact = element.value
        System.out.printf("Main: Last Entry: %s: %s\n", contact.name, contact.phone)

        /*
		 * Write a subset of the map
		 */
        System.out.printf("Main: Submap from A1996 to B1002: \n")
        val submap = map.subMap("A1996", "B1002")
        do {
            element = submap.pollFirstEntry()
            if (element != null) {
                contact = element.value
                System.out.printf("%s: %s\n", contact.name, contact.phone)
            }
        } while (element != null)
    }
}