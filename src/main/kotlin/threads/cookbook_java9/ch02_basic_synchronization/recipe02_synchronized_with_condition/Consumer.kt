package threads.cookbook_java9.ch02_basic_synchronization.recipe02_synchronized_with_condition

class Consumer(val storage: EventStorage) : Runnable {

    /**
     * Core method of the consumer. Consume 100 events.
     */
    override fun run() {
        (0 until 100).forEach { storage.get() }
    }
}