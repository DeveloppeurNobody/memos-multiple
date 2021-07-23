package threads.cookbook_java9.ch02_basic_synchronization.recipe02_synchronized_with_condition

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        // Creates an event storage
        val storage = EventStorage();

        // Creates a Producer and a Thread to run it
        val producer = Producer(storage);
        val threadProducer = Thread(producer);

        // Creates a Consumer and a Thread to run it
        val consumer = Consumer(storage);
        val threadConsumer = Thread(consumer);

        // Starts the threads
        threadConsumer.start();
        threadProducer.start();
    }
}