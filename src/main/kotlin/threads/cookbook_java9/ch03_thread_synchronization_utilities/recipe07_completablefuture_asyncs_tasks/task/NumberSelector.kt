package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task

import java.util.function.Function;

class NumberSelector : Function<MutableList<Long>, Long> {

    override fun apply(t: MutableList<Long>): Long {
        println("${Thread.currentThread().name}: Step 3: Start");
        val max = t.stream()
            .max(Long::compareTo)
            .get();

        val min = t.stream()
            .min(Long::compareTo)
            .get();

        val result: Long = (max + min) / 2;
        println("${Thread.currentThread().name}: Step 3: Result - $result");

        return result;
    }
}