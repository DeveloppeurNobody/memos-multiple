package threads.cookbook_java9.ch02_basic_synchronization.recipe02_synchronized_with_condition

class Producer(val storage: EventStorage) : Runnable {

    /**
     * Core method of the producer. Generates 100 events.
     */
    override fun run() {
        (0 until 100).forEach { storage.set(); }
    }
}