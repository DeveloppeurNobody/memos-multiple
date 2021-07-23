package threads.cookbook_java9.ch07_concurrent_collections.recipe01_non_blocking_thread_safe_deques.task

import java.util.concurrent.ConcurrentLinkedDeque

/**
 * Task that add 10000 elements to a ConcurrentListDeque
 */
class AddTask(val list: ConcurrentLinkedDeque<String>) : Runnable {

    /**
     * Main method of the class. Add 10000 elements to the list using the add()
     * method that adds the element at the end of the list
     */
    override fun run() {
        val name = Thread.currentThread().name;

        repeat(10000) {
            list.add("$name: Element $it");
        }
    }
}

