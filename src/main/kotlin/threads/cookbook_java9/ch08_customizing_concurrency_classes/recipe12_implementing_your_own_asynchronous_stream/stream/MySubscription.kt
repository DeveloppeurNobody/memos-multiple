package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream

import java.util.concurrent.Flow

class MySubscription : Flow.Subscription {
    private var canceled = false;
    private var requested = 0L;

    override fun cancel() {
        canceled = true;
    }

    override fun request(n: Long) {
        requested += n;
    }

    fun isCanceled(): Boolean = canceled;

    fun getRequested(): Long = requested;

    fun decreaseRequested() {
        requested--;
    }
}