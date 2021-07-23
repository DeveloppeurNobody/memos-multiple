package threads.cookbook_java9.ch02_basic_synchronization.recipe02_synchronized_with_condition

import java.util.*


/**
 * This class implements an EventStorage.
 * Producers will storage events in it
 * and Consumers will process them.
 * An event will be a java.util.Date object.
 */
class EventStorage : Object(){
    /**
     * Maximum size of the storage
     */
    private val maxSize: Int = 10

    /**
     * Storage of events
     */
    private val storage: Queue<Date>

    /**
     * This method creates and storage an event.
     */
    @Synchronized
    fun set() {
        while (storage.size == maxSize) {
            try {
                wait()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        storage.add(Date())
        System.out.printf("Set: %d\n", storage.size)
        notify()
    }

    /**
     * This method delete the first event of the storage.
     */
    @Synchronized
    fun get() {
        while (storage.size == 0) {
            try {
                wait()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        val element = storage.poll().toString()
        System.out.printf("Get: %d: %s\n", storage.size, element)
        notify()
    }

    /**
     * Constructor of the class. Initializes the attributes.
     */
    init {
        storage = LinkedList()
    }
}
