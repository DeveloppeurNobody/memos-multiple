package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task


/**
 * This class implements the Consumer of the events. There is only
 * one consumer in the example that consumes 1002 events
 *
 */
class Consumer(buffer: MyPriorityTransferQueue<Event?>?) :
    Runnable {
    /**
     * Buffer from which the consumer takes the events
     */
    private val buffer: MyPriorityTransferQueue<Event?>? = buffer

    /**
     * Main method of the consumer. It takes 1002 events from the buffer
     */
    override fun run() {
        for (i in 0..1001) {
            try {
                val value: Event? = buffer?.take()
                System.out.printf("Consumer: %s: %d\n", value?.thread, value?.priority)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}
