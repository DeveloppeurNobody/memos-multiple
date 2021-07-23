package threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer

import threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task.Buffer
import threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task.Consumer
import threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task.Producer
import threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.utils.FileMock
import java.util.stream.IntStream

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates a simulated file with 100 lines
        val mock = FileMock(101, 10);

        // Creates a buffer with a maximum of 20 lines
        val buffer = Buffer(20);

        // Creates a producer and a thread to run it
        val producer = Producer(mock, buffer);
        val producerThread = Thread(producer, "Producer");

        // Creates three consumers and threads to run them
        val consumers = arrayOfNulls<Consumer>(3);
        val consumersThreads = arrayOfNulls<Thread>(3);

        (0 until 3).forEach {
            consumers[it] = Consumer(buffer);
            consumersThreads[it] = Thread(consumers[it], "Consumer $it");
        }

        // Strats the producer and the consumers
        producerThread.start();
        (0 until 3).forEach {
            consumersThreads[it]?.start();
        }
    }
}