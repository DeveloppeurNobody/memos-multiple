package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe08_verifying_elements_of_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Person
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val persons = PersonGenerator.generatePersonList(10);

        println("***");
        val maxSalary = persons
            .parallelStream()
            .map { it.salary }
            .max(Int::compareTo)
            .get();

        val minSalary = persons
            .parallelStream()
            .mapToInt { it.salary }
            .min()
            .asInt;

        println("Salaries are between $minSalary and $maxSalary");

        var condition = false;

        println("****");
        condition = persons
            .parallelStream()
            .allMatch { it.salary > 0 };

        println("Salary > 0: $condition \n");


        println("***");
        condition = persons
            .parallelStream()
            .allMatch { it.salary > 10000 };

        println("Salary > 10000: $condition \n");

        println("***");
        condition = persons
            .parallelStream()
            .allMatch { it.salary > 30000 };

        println("Salary > 30000: $condition \n");

        // anyMatch()
        condition = persons
            .parallelStream()
            .anyMatch { it.salary > 50000 }
        println("Any with salary > 50000: $condition \n");

        // anyMatch()
        condition = persons
            .parallelStream()
            .anyMatch { it.salary > 100000 };
        println("Any with salary > 100000: $condition \n");

        // noneMatch()
        condition = persons
            .parallelStream()
            .noneMatch { it.salary > 100000 };
        println("None with salary > 100000: $condition \n");

        var person: Person;
        println("***");

        person = persons
            .parallelStream()
            .findAny()
            .get();
        println("Any: $person");

        person = persons
            .parallelStream()
            .findFirst()
            .get();
        println("First: $person");

        person = persons
            .parallelStream()
            .sorted { p1, p2 -> p1.salary - p2.salary }
            .findFirst()
            .get();
        println("First Sorted: $person");
        println("*** end ***");
    }
}