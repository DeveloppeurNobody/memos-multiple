package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe06_exchanger_with_producer_consumer_example

import java.util.concurrent.Exchanger

class Consumer(var buffer: MutableList<String>,
               var exchanger: Exchanger<MutableList<String>>) : Runnable {

    /**
     * Main method of the consumer. It consumes all the events produced by the Producer.
     * After processes ten events. it uses the exchanger object to synchronize with the producer.
     * It sends to the producer an empty buffer and receives a buffer with 10 events
     */
    override fun run() {
        (1..10).forEach { cycle ->
            println("---------------- Consumer: Cycle $cycle");

            try {
              // Wait for the produced data and send the empty buffer to the producer
                buffer = exchanger.exchange(buffer);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }

            println("Consumer: ${buffer.size}");

            repeat(10) {j ->
                val message = buffer.get(0);
                println("Consumer: $message");
                buffer.removeAt(0);
            }
        }
    }
}