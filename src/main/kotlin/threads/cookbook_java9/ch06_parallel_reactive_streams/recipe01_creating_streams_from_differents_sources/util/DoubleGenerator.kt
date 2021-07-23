package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util

import java.util.stream.DoubleStream

class DoubleGenerator {
    companion object {
        fun generateDoubleList(size: Int, max: Int): MutableList<Double> {
            val random = java.util.Random();
            val numbers = mutableListOf<Double>();

            repeat(size) {
                val value = random.nextDouble() * max;
                numbers.add(value);
            }
            return numbers;
        }

        fun generateStreamFromList(list: List<Double>): DoubleStream {
            val builder = DoubleStream.builder();

            list.forEach { builder.add(it) }

            return builder.build();
        }
    }
}