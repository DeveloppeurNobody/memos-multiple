package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.data.News

class PublisherTask (val consumerData: ConsumerData,
                     val news: News): Runnable {

    override fun run() {
        val subscription = consumerData.subscription;
        if ( !(subscription?.isCanceled()!! && (subscription.getRequested() > 0))) {
            consumerData.consumer?.onNext(news);
            subscription.decreaseRequested();
        }
    }
}