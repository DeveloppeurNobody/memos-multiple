package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe02_reducing_elements_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.DoubleGenerator
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Point
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PointGenerator
import java.util.*
import java.util.stream.DoubleStream

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Main: Examples of reduce methods.\n");

        // Working with a strems of numbers
        val numbers = DoubleGenerator.generateDoubleList(10000, 1000);
        println("***\n");

        // Getting the number of elements
        var doubleStream: DoubleStream = DoubleGenerator.generateStreamFromList(numbers);
        val numberOfElements = doubleStream.parallel()
            .count();
        println("The list of numbers has $numberOfElements");
        println("***");

        // Getting the of the numbers
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        var sum = doubleStream.parallel()
            .sum();
        println("Its numbers sum: $sum");
        println("***");

        // Getting the average of the numbers
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        var average = doubleStream.parallel()
            .average()
            .asDouble;
        println("Its numbers have an average value of $average");

        // Getting the highest value
        println("***")
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        var max = doubleStream.parallel()
            .max()
            .asDouble;
        println("The maximum value in the list is $max");

        // Getting the lowest value
        println("***");
        doubleStream = DoubleGenerator.generateStreamFromList(numbers);
        var min = doubleStream.parallel()
            .min()
            .asDouble;
        println("The minimum value in the list is $min");

        // Reduce method - First version
        println("***")
        println("Reduce - First Version");
        val points = PointGenerator.generatePointList(10000);
        val point: Optional<Point> = points.parallelStream()
            .reduce { p1, p2 ->
                val p = Point();
                p.x = p1.x + p2.x;
                p.y = p1.y + p2.y;
                p;
            }
        println("${point.get().x}:${point.get().y}");
        println("***\n");

        // Reduce method - Second version
        println("***");
        println("Reduce, second version");
        val persons = PersonGenerator.generatePersonList(10000);

        val totalSalary = persons.parallelStream()
            .map { p -> p.salary }
            .reduce(0) { s1, s2 -> s1 + s2 };
        println("Total salary: $totalSalary");
        println("***\n");

        // Resuce method. Third version
        println("***");
        println("Reducen third version");

        var value = 0
        value = persons.parallelStream().reduce(value,
            { n: Int, (_, _, _, _, salary) ->
                if (salary > 50000) {
                    return@reduce n + 1
                } else {
                    return@reduce n
                }
            },
            { n1: Int, n2: Int -> n1 + n2 }
        );

        println("""
            The number of people with a salary bigger that 50,000 is $value
            
        """.trimIndent())

    }
}