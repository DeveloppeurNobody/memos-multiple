package threads.cookbook_java9.ch07_concurrent_collections.recipe01_non_blocking_thread_safe_deques.task

import java.util.concurrent.ConcurrentLinkedDeque

/**
 * Task that delete 10000 elements from a ConcurrentListDeque
 */
class PollTask(val list: ConcurrentLinkedDeque<String>) : Runnable {

    /**
     * Main method of the task. Deletes 10000 elements from the list using the
     * pollFirst() that deletes the first element of the list and pollLast()
     * that deletes the last element of the list
     */
    override fun run() {
        repeat(5000) {
            list.pollFirst();
            list.pollLast();
        }
    }
}