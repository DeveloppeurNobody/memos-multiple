package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe06_exchanger_with_producer_consumer_example

import java.util.concurrent.Exchanger

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates two buffers
        val buffer1 = mutableListOf<String>();
        val buffer2 = mutableListOf<String>();

        // Creates the exchanger
        val exchanger = Exchanger<MutableList<String>>();

        // Creates the producer
        val producer = Producer(buffer1, exchanger);

        // Creates the consumer
        val consumer = Consumer(buffer2, exchanger);

        // Creates and starts the threads
        val threadProducer = Thread(producer);
        val threadConsumer = Thread(consumer);

        threadProducer.start()
        threadConsumer.start();
    }
}