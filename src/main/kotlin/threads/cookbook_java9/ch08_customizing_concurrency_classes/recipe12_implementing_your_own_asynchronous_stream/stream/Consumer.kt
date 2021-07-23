package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.data.News
import java.util.concurrent.Flow

class Consumer (val name: String) : Flow.Subscriber<News> {
    lateinit var subscription: Flow.Subscription;

    override fun onComplete() {
        println("$name - ${Thread.currentThread().name}: Consumer - Complete\n");
    }

    override fun onSubscribe(subscription: Flow.Subscription?) {
        if (subscription != null) {
            this.subscription = subscription
            this.subscription.request(1);
            println("${Thread.currentThread().name}: Consumer - Subscription\n");
        };


    }

    override fun onNext(item: News?) {
        println("""
            $name - ${Thread.currentThread().name}: Consumer - News
            $name - ${Thread.currentThread().name}: Title: ${item?.title}
            $name - ${Thread.currentThread().name}: Content: ${item?.content}
            $name - ${Thread.currentThread().name}: Date: ${item?.date}
        """.trimIndent());
        subscription.request(1);
    }

    override fun onError(throwable: Throwable?) {
        println("${name} - ${Thread.currentThread().name}: Consumer - Error: ${throwable?.message}");
    }
}