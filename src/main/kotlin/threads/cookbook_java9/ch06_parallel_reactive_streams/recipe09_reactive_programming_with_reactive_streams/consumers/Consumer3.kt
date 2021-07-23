package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.consumers

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe09_reactive_programming_with_reactive_streams.items.Item
import java.util.concurrent.Flow

class Consumer3 : Flow.Subscriber<Item> {
    override fun onComplete() {
        println("${Thread.currentThread().name}: Consumer 3: Completed\n");
    }

    override fun onSubscribe(subscription: Flow.Subscription?) {
        println("""
            ${Thread.currentThread().name}: Consumer 3: Subscription received
            ${Thread.currentThread().name}: Consumer 3: Requested three items
            
        """.trimIndent());
        subscription?.request(3);
    }

    override fun onNext(item: Item?) {
        println("""
            ${Thread.currentThread().name}: Consumer 3: Item received
            ${Thread.currentThread().name}: Consumer 3: ${item?.title}
            ${Thread.currentThread().name}: Consuemr 3: ${item?.content}
        """.trimIndent());
    }

    override fun onError(throwable: Throwable?) {
        println("${Thread.currentThread().name}: Consumer 3: Error\n");
        throwable?.printStackTrace(System.err);
    }
}