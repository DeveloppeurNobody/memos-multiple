package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier

class MySupplier : Supplier<String> {
    private val counter = AtomicInteger(0);

    override fun get(): String {
        val value = counter.getAndAdd(1);
        return "String $value";
    }
}