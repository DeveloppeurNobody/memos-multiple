package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.items.Item
import java.util.concurrent.Flow

class Consumer2 : Flow.Subscriber<Item> {
    private lateinit var subscription: Flow.Subscription;

    override fun onComplete() {
        println("${Thread.currentThread().name}: Consumer2: Completed");
    }

    override fun onSubscribe(subscription: Flow.Subscription?) {
        println("${Thread.currentThread().name}: Consumer2: Subscription received");
        if (subscription != null) {
            this.subscription = subscription;
            subscription.request(1);
        };
    }

    override fun onNext(item: Item?) {
        println("""
            ${Thread.currentThread().name}: Consumer2: Item received
            ${Thread.currentThread().name}: Consumer2: ${item?.title}
            ${Thread.currentThread().name}: Consumer2: ${item?.content}
        """.trimIndent());
    }

    override fun onError(throwable: Throwable?) {
        println("${Thread.currentThread().name}: Consumer2: Error");
        throwable?.printStackTrace(System.err);
    }
}