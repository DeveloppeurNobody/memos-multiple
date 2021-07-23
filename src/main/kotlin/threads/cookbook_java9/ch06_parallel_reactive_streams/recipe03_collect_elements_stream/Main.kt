package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe03_collect_elements_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Counter
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Person
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.stream.Collector
import java.util.stream.Collectors


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("***");
        println("Main: Examples of collect methods.");

        // Generating a list of persons
        val persons = PersonGenerator.generatePersonList(100);

        // Collectors.groupingByConcurrent
        println("""
            Grouping By Concurrent
            Concurrent: ${Collectors.groupingByConcurrent<Any, Any?>{ p -> p}.characteristics().contains(Collector.Characteristics.CONCURRENT) }
        """.trimIndent());

        val personsByName = persons.parallelStream()
            .collect(Collectors.groupingByConcurrent(Person::firstName));
        personsByName.keys
            .forEach { key ->
                val listOfPersons = personsByName.get(key);
                println("$key: There are ${listOfPersons?.size} with that name");
            }

        println("***");
        println("");

        // Collectors.joining
        println("""
            ***
            Joining
            Concurrent: ${Collectors.joining().characteristics().contains(Collector.Characteristics.CONCURRENT)}
        """.trimIndent());
        val message = persons
            .parallelStream()
            .map { it.toString() }
            .collect(Collectors.joining(","));
        println(message);
        println("***");

        // Collectors.paritionBy
        println("""
            ***
            Partitioning By
            Concurrent: ${Collectors.partitioningBy { p: Person -> true}.characteristics().contains(Collector.Characteristics.CONCURRENT)}
        """.trimIndent());

        val personsBySalary = persons.parallelStream()
            .collect(Collectors.partitioningBy {p -> p.salary > 50000});
        personsBySalary.keys
            .forEach { key ->
                val listOfPersons = personsBySalary.get(key);
                println("$key: ${listOfPersons?.size}");
            }

        println("***");

        // Collectors.toConcurrentMap
        println("""
            ***
            To Concurrent Map
            Concurrent: ${Collectors.toConcurrentMap<Any, Any, Any>({ p -> p}, { p -> p}).characteristics().contains(Collector.Characteristics.CONCURRENT)}
        """.trimIndent());

        val nameMap: ConcurrentMap<String, String> = persons.parallelStream()
            .collect(Collectors.toConcurrentMap(
                { p: Person -> p.firstName },
                { p: Person -> p.lastName },
                { s1: String, s2: String -> "$s1, $s2"}
            ));

        nameMap.forEach { (key, value) ->
            println("$key: $value");
        }
        println("***");

        // Collect, first example
        println("***");
        println("Collect, first example\n");

        val highSalary = persons
            .parallelStream()
            .collect(
                { mutableListOf() },
                { list: MutableList<Person>, person: Person -> if (person.salary > 50000) { list.add(person)} },
                { obj: MutableList<Person>, c: MutableList<Person> -> obj.addAll(c) }
            );
        println("""
            High Salary People: ${highSalary.size}
            ***
            
        """.trimIndent());

        // Collect, second example
        val peoplesNames: ConcurrentMap<String, Counter> = persons
            .parallelStream()
            .collect(
                { ConcurrentHashMap() },
                { hash, person ->
                    hash.computeIfPresent(person.firstName, { name: String, counter: Counter -> counter.increment(); counter });
                    hash.computeIfAbsent(person.firstName, { name: String -> val c = Counter(); c.value = name; c});
                },
                { hash1: ConcurrentHashMap<String, Counter>, hash2: ConcurrentHashMap<String, Counter> ->
                    hash2.forEach(10) { key: String, value: Counter ->
                        hash1.merge(key, value) { v1, v2 -> v1.counter = (v1.counter + v2.counter); v1};
                    }
                }
            );
        peoplesNames.forEach { name: String, counter: Counter ->
            println("$name:$counter");
        }
        println("***");
    }
}