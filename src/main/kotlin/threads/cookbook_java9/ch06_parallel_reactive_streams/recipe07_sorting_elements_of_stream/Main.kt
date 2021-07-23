package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe07_sorting_elements_of_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Person
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import java.util.*
import java.util.stream.Collectors

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Sorted array of integer
        val numbers = arrayOf(9,8,7,6,5,4,3,2,1,2,3,4,5,6,7,8,9);
        println("***");
        listOf<Int>(*numbers)
            .stream()
            .parallel()
            .sorted()
            .forEachOrdered(::println);

        println("***\n");

        // Sorted for persons
        println("***");
        val persons = PersonGenerator.generatePersonList(10);
        persons
            .parallelStream()
            .sorted()
            .forEachOrdered { println("${it.lastName}, ${it.firstName}") }

        println("***\n");

        // Unordered
        println("***");
        var person = persons[0];
        println("${person.firstName} ${person.lastName}");

        val personSet = TreeSet<Person>(persons);
        repeat(10) {
            println("************ $it ****************");
            person = personSet
                .stream()
                .parallel()
                .limit(1)
                .collect(Collectors.toList())
                .get(0);

            println("${person.firstName} ${person.lastName}");

            person = personSet
                .stream()
                .unordered()
                .parallel()
                .limit(1)
                .collect(Collectors.toList())
                .get(0);

            println("${person.firstName} ${person.lastName}");
        }

    }
}