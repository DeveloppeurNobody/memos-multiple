package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task.Consumer
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task.Event
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task.MyPriorityTransferQueue
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task.Producer
import java.util.concurrent.TimeUnit


object Main {
    /**
     * @param args
     */
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        /*
		 * Create a Prioriy Transfer Queue
		 */
        val buffer: MyPriorityTransferQueue<Event?> = MyPriorityTransferQueue<Event?>()

        /*
		 * Create a Producer object
		 */
        val producer = Producer(buffer)

        /*
		 * Launch 10 producers
		 */
        val producerThreads = arrayOfNulls<Thread>(10)
        for (i in producerThreads.indices) {
            producerThreads[i] = Thread(producer)
            producerThreads[i]!!.start()
        }

        /*
		 * Create and launch the consumer
		 */
        val consumer = Consumer(buffer)
        val consumerThread = Thread(consumer)
        consumerThread.start()

        /*
		 * Write in the console the actual consumer count
		 */System.out.printf("Main: Buffer: Consumer count: %d\n", buffer.getWaitingConsumerCount())

        /*
		 * Transfer an event to the consumer
		 */
        var myEvent = Event("Core Event", 0)
        buffer.transfer(myEvent)
        System.out.printf("Main: My Event has ben transfered.\n")

        /*
		 * Wait for the finalization of the producers
		 */for (i in producerThreads.indices) {
            producerThreads[i]!!.join()
        }

        /*
		 * Sleep the thread for one second
		 */TimeUnit.SECONDS.sleep(1)

        /*
		 * Write the actual consumer count
		 */System.out.printf("Main: Buffer: Consumer count: %d\n", buffer.getWaitingConsumerCount())

        /*
		 * Transfer another event
		 */myEvent = Event("Core Event 2", 0)
        buffer.transfer(myEvent)

        /*
		 * Wait for the finalization of the consumer
		 */consumerThread.join()

        /*
		 * Write a message indicating the end of the program
		 */System.out.printf("Main: End of the program\n")
    }
}
