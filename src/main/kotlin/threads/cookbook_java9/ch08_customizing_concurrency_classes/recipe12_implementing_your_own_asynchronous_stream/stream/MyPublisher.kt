package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.data.News
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.Executors
import java.util.concurrent.Flow
import java.util.concurrent.ThreadPoolExecutor

class MyPublisher : Flow.Publisher<News> {
    private val consumers = ConcurrentLinkedDeque<ConsumerData>();
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()) as ThreadPoolExecutor;

    override fun subscribe(subscriber: Flow.Subscriber<in News>?) {
        val consumerData = ConsumerData();
        consumerData.consumer = subscriber as Consumer;

        val subscription = MySubscription();
        consumerData.subscription = subscription;

        subscriber.onSubscribe(subscription);

        consumers.add(consumerData);
    }

    fun publish(news: News) {
        consumers.forEach { consumerData ->
            try {
                executor.execute(PublisherTask(consumerData, news));
            } catch (e: Exception) {
                System.err.println(e)
            }
        }
    }
}