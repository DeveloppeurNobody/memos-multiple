package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.items.Item
import java.util.concurrent.Flow

class Consumer1 : Flow.Subscriber<Item> {
    override fun onComplete() {
        println("${Thread.currentThread().name}: Consumer1: Completed");
    }

    override fun onSubscribe(subscription: Flow.Subscription?) {
        println("""
            ${Thread.currentThread().name}: Consumer1: Subscription received
            ${Thread.currentThread().name}: Consumer1: No Items requested
        """.trimIndent())
    }

    override fun onNext(item: Item?) {
        println("""
            ${Thread.currentThread().name}: Consumer1: Item received
            ${Thread.currentThread().name}: Consumer1: ${item?.title}
            ${Thread.currentThread().name}: Consumer1: ${item?.content}
        """.trimIndent());
    }

    override fun onError(throwable: Throwable?) {
        println("${Thread.currentThread().name}: Consumer1: Error")
        throwable?.printStackTrace();
    }
}