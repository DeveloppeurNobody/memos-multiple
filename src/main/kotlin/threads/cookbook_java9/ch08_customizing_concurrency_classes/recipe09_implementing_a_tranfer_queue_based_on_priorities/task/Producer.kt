package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task

/**
 * This class implements the producers of data. It store 100
 * events in the queue with incremental priority
 *
 */
class Producer(val buffer: MyPriorityTransferQueue<Event?>?) :
    Runnable {
    /**
     * Buffer used to store the events
     */


    /**
     * Main method of the producer. Store 100 events in the buffer with
     * incremental priority
     */
    override fun run() {
        for (i in 0..99) {
            val event = Event(Thread.currentThread().name, i)
            buffer?.put(event);
        }
    }

}
