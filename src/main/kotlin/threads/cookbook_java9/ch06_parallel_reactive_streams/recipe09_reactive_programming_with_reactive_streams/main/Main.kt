package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.main

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers.Consumer1
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers.Consumer2
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers.Consumer3
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.items.Item
import java.util.concurrent.SubmissionPublisher
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val consumer1 = Consumer1();
        val consumer2 = Consumer2();
        val consumer3 = Consumer3();

        val publisher: SubmissionPublisher<Item> = SubmissionPublisher();

        publisher.subscribe(consumer1);
        publisher.subscribe(consumer2);
        publisher.subscribe(consumer3);

        repeat(10) {
            val item = Item();
            item.title = "Item $it";
            item.content = "This is the item $it";
            publisher.submit(item);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        }

        publisher.close();
    }
}