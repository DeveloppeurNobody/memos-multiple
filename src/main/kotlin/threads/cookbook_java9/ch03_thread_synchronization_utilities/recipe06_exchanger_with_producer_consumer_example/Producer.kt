package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe06_exchanger_with_producer_consumer_example

import java.util.concurrent.Exchanger

class Producer(
    var buffer: MutableList<String>,
    val exchanger: Exchanger<MutableList<String>>) : Runnable {

    /**
     * Buffer to save the events produced
     */
    // val buffer: MutableList<String>;

    /**
     * Exchanger to synchronize with the consumer
     */
    // val exchanger: Exchanger<MutableList<String>>;

    /**
     * Main method of the producer. It produces 100 events. 10 cycles of 10 events.
     * After produce 10 events, it uses the exchanger object to synchronize with the consumer.
     * The producer sends to the consumer the buffer with ten events and receives from the consumer
     * an empty buffer
     */
    override fun run() {
        (1..10).forEach {cycle ->
            println("-------- Producer: Cycle $cycle");
            repeat(10) {j ->
                val message = "Event ${(((cycle - 1) * 10) + j)}";
                println("Producer: $message");
                buffer.add(message);
            }

            try {
                /**
                 * Change the data buffer with the consumer
                 */
                buffer = exchanger.exchange(buffer);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }

            println("Producer: ${buffer.size}");
        }
    }
}