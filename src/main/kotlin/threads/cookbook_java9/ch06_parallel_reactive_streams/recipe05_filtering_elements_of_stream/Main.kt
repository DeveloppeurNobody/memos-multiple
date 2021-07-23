package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe05_filtering_elements_of_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import java.util.*


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val persons = PersonGenerator.generatePersonList(10);

        // Distinct with objects
        println("""
            ***
            Original List
            
        """.trimIndent());

        persons.parallelStream()
            .forEach { println("${it.lastName}: ${it.firstName}") };
        println("***");

        println("List without duplicates");
        persons.parallelStream()
            .distinct()
            .forEach { println("${it.lastName}: ${it.firstName}") };
        println("***");

        // Distinct with numbers
        println("""
            ***
            Array of numbers without duplicates
            
        """.trimIndent());
        val numbers = arrayOf(1, 3, 2, 1, 2, 2, 1, 3, 3, 1, 1, 3, 2, 1)
        listOf(*numbers)
            .parallelStream()
            .mapToInt { n: Int? -> n!! }
            .distinct()
            .forEach { println("Number: $it") }

        println("***");

        // Filter with objects
        println("""
            ***
            Filter with persons
            
        """.trimIndent());

        persons
            .parallelStream()
            .filter { it.salary < 30000 }
            .forEach { println("${it.lastName}, ${it.firstName}") }
        println("""
            ***
        """.trimIndent());

        // Filter with numbers
        println("""
            ***
            Filter with numbers: 
        """.trimIndent());
        listOf(*numbers)
            .parallelStream()
            .mapToInt { n: Int? -> n!! }
            .filter { it < 2 }
            .forEach(::println);
        println("""
            ***
            
        """.trimIndent());

        // Limit and skip with numbers
        println("""
            ***
            Limit with numbers:
            
        """.trimIndent());
        persons
            .parallelStream()
            .mapToDouble { it.salary.toDouble() }
            .sorted()
            .limit(5)
            .forEach { println("Limit: $it") };

        println("""
            ***
            Skip with numbers: 
            
        """.trimIndent());

        persons
            .parallelStream()
            .mapToDouble { it.salary.toDouble() }
            .sorted()
            .skip(5)
            .forEach { println("Skip: $it") }

        println("***");



//        listOf(*numbers).parallelStream()
//            .mapToInt { n: Int? -> n!! }
//            .distinct()
//            .forEach { n: Int -> System.out.printf("Number: %d\n", n) }
    }
}