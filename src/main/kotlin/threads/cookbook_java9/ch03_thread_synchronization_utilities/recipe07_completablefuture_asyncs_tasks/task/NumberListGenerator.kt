package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task

import java.util.function.Supplier
import kotlin.math.round
import kotlin.math.roundToLong

class NumberListGenerator(val size: Int) : Supplier<MutableList<Long>> {

    override fun get(): MutableList<Long> {
        var ret = mutableListOf<Long>();
        println("${Thread.currentThread().name}: NumberListGenerator : Start");

        repeat((size * 1_000_000)) {
            val number = round(Math.random() * Long.MAX_VALUE).toLong();
            ret.add(number);
        }

        println("${Thread.currentThread().name} : NumberListGenerator : End");

        return ret;
    }
}