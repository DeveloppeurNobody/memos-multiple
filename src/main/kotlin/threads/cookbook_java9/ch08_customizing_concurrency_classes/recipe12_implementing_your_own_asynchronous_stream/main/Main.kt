package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.main

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.data.News
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream.Consumer
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream.MyPublisher
import java.util.*
import java.util.concurrent.Flow
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val publisher = MyPublisher();

        val consumer1: Flow.Subscriber<News> = Consumer("Consumer 1");
        val consumer2: Flow.Subscriber<News> = Consumer("Consumer 2");

        publisher.subscribe(consumer1);
        publisher.subscribe(consumer2);

        println("Main: Start\n");

        var news = News();
        with(news) {
            title = "My First news";
            content = "This is the content";
            date = Date();
        }

        publisher.publish(news);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }

        news = News();
        with(news) {
            title = "My Second news";
            content = "This is the content of the second news";
            date = Date();
        }

        publisher.publish(news);

        println("Main: End\n");
    }
}