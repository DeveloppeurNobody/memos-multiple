package threads.cookbook_java9.ch01_thread_management.recipe10_thread_factory.factory

import java.util.*
import java.util.concurrent.ThreadFactory

/**
 * Class that implements the ThreadFactory interface
 * to create a basic thread factory
 */
class MyThreadFactory(var name: String,
                      var counter: Int = 0,
                      var stats: MutableList<String> = mutableListOf()) : ThreadFactory {
    /**
     *  Attributes to save the necessary data to the factory
     */
//    private var counter: Int = 0
//    private val name: String? = null
//    private val stats: MutableList<String>? = null


    /**
     * Method that creates a new thread object using a Runnable object.
     * @param r: Runnable object to create the new Thread.
     */
    override fun newThread(r: Runnable): Thread {
        // Create the new Thread object
        val t = Thread(r, "$name -Thread_$counter");
        counter++;

        // Actualize the statistics of the factory
        stats.add("Created thread ${t.id} with name ${t.name} on ${Date()}\n");
        return t;
    }

    /**
     * Method that returns the statistics of the ThreadFactory
     * @return the statistics of the ThreadFactory
     */
    fun getStats(): String {
        val buffer = StringBuffer();

        stats.forEach {
            buffer.append(it);
        }

        return buffer.toString();
    }
}