package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe05_monitoring_a_stream

import java.util.concurrent.atomic.AtomicLong

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val counter = AtomicLong(0);
        val random = java.util.Random();

        var streamCounter = random.doubles(1000)
            .parallel()
            .peek { number ->
                val actual = counter.incrementAndGet();
                println("$actual - $number");
            }
            .count();

        println("""
            Counter: ${counter.get()}
            Stream Counter: $streamCounter
        """.trimIndent());

        counter.set(0);
        random.doubles(1000)
            .parallel()
            .peek { number ->
                val actual = counter.incrementAndGet();
                println("Peek: $actual - $number");
            }
            .forEach { println("For Each: $it \n") }

        println("Counter: ${counter.get()}");

    }
}