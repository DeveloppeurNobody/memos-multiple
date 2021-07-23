package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe12_implementing_your_own_asynchronous_stream.stream

data class ConsumerData (var consumer: Consumer? = null,
                         var subscription: MySubscription? = null)