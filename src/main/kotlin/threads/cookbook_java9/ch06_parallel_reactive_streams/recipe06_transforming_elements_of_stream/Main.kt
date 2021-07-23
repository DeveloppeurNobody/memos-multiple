package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe06_transforming_elements_of_stream

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.BasicPerson
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.FileGenerator
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.DoubleStream
import java.util.stream.Stream


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a list of persons
        val persons = PersonGenerator.generatePersonList(100);

        // Map to Double
        val ds: DoubleStream = persons
            .parallelStream()
            .mapToDouble { it.salary.toDouble() };

        ds.distinct()
            .forEach {  println("Salary: $it") }

        val size: Long = persons
            .parallelStream()
            .distinct()
            .count()

        println("size: $size");

        // Map To Object
        println("***")
        val basicPersons = persons
            .parallelStream()
            .map { p ->
                val bp = BasicPerson();
                bp.name = "${p.firstName} ${p.lastName}";
                bp.age = getAge(p.birthDate);
                bp;
            }
            .collect(Collectors.toList());

        basicPersons.forEach { println("${it.name} ${it.age}") }
        println("***");

        // FlatMap
        println("***");
        val file: List<String> = FileGenerator.generateFile(100);
        var wordCount: Map<String?, Long> = file
            .parallelStream()
            .flatMap { line: String -> Stream.of(*line.split("[ ,.]".toRegex()).toTypedArray()) }
            .filter { w: String -> w.isNotEmpty() }
            .sorted()
            .collect(
                Collectors.groupingByConcurrent(
                    { e: String? -> e },
                    Collectors.counting()
                )
            );

        wordCount.forEach { k, v -> println("$k: $v")}
        println("***");

    }

    private fun getAge(birthDate: Date): Long {
        val start: LocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val now = LocalDate.now()
        return ChronoUnit.YEARS.between(start, now)
    }

}