package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe04_applying_action_to_each_element_of_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.DoubleGenerator
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val persons = PersonGenerator.generatePersonList(10);

        // For each parallel
        println("""
            ***
            Parallel forEach():
            
        """.trimIndent());

        persons.parallelStream()
            .forEach { println("${it.lastName}: ${it.firstName}") };

        println("***\n");

        // For each ordered
        val doubles = DoubleGenerator.generateDoubleList(10, 100);

        // For each ordered parallel with numbers
        println("***");
        println("Parallel forEachOrdered() with numbers\n");
        doubles.parallelStream()
            .sorted()
            .forEachOrdered(::println);

        println("***\n");

        // For each after sort with numbers
        println("""
            ***
            Parallel forEach() after sorted() with numbers
            
        """.trimIndent());

        doubles.parallelStream()
            .sorted()
            .forEach(::println);

        println("***\n");

        // For each ordered parallel with objects
        println("""
            ***
            Parallel forEachOrdered with Persons
            
        """.trimIndent());
        persons.parallelStream()
            .sorted()
            .forEachOrdered { println("${it.lastName}: ${it.firstName}")};
        println("""
            ***
            
        """.trimIndent());

        // Peek
        println("""
            ***
            Peek
            
        """.trimIndent());

        doubles.parallelStream()
            .peek { println("Step 1: Number $it")}
            .filter { it < 50 }
            .peek { println("Step 2: Number $it") }
            .forEach { println("Final Step: Number: $it") }

        println("***");


    }
}