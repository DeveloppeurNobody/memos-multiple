package threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources

import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.MySupplier
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.Person
import threads.cookbook_java9.ch06_parallel_reactive_streams.recipe01_creating_streams_from_differents_sources.util.PersonGenerator
import java.io.BufferedReader
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.function.Supplier
import java.util.stream.DoubleStream
import java.util.stream.Stream

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("""
            Main: Generating parallel streams from different sources
            ********************************************************
            
        """.trimIndent());

        /////////////////////////////////////////////////////////////
        // Creating a stream from a Collection
        /////////////////////////////////////////////////////////////
        println("""
            ********************************************************
            From a Collections:
        """.trimIndent());
        val persons = PersonGenerator.generatePersonList(10000);
        val personStream: Stream<Person> = persons.parallelStream();
        println("""
            Number of persons: ${personStream.count()}
            ********************************************************
            
        """.trimIndent());

        /////////////////////////////////////////////////////////////
        // Using a generator
        /////////////////////////////////////////////////////////////
        println("""
            ********************************************************
            From a Supplier:
            
        """.trimIndent());
        val supplier: Supplier<String> = MySupplier();
        val generatorStream: Stream<String> = Stream.generate(supplier);
        generatorStream.parallel()
            .limit(10)
            .forEach(::println);

        println("""
            ********************************************************
            
        """.trimIndent());

        /////////////////////////////////////////////////////////////
        // From predefined elements
        /////////////////////////////////////////////////////////////
        println("""
            ********************************************************
            From a predefined set of elements:
            
        """.trimIndent());
        val elementsStream: Stream<String> = Stream.of("Peter", "John", "Mary");
        elementsStream.parallel()
            .forEach(::println);
        println("""
            ********************************************************
            
        """.trimIndent());

        /////////////////////////////////////////////////////////////
        // From a File
        /////////////////////////////////////////////////////////////
        println("""
            ********************************************************
            From a file:
            
        """.trimIndent());
        try {
            BufferedReader(FileReader("/home/ryu/log_threads.txt"))
                .use { br ->
                    val fileLines = br.lines();
                    println("Number of line in the file: ${fileLines.parallel().count()}");
                    println("***********************************************************\n");
                }
        } catch (ie: Exception) {
            System.err.println(ie);
        }

        /////////////////////////////////////////////////////////////
        // From a directory
        /////////////////////////////////////////////////////////////
        println("""
            *********************************************************
            From a Directory:

        """.trimIndent());
        try {
            val directoryContent: Stream<Path> = Files.list(Paths.get(System.getProperty("user.home")));
            println("Number of elements (files and folders): ${directoryContent.parallel().count()}");
            directoryContent.close();
            println("""
                *********************************************************
                
            """.trimIndent())
        } catch (ie: Exception) {
            System.err.println(ie);
        }

        //////////////////////////////////////////////////////////////
        // From array
        //////////////////////////////////////////////////////////////
        println("""
            **********************************************************
            From an Array:
            
        """.trimIndent());
        val array = arrayOf("1", "2", "3", "4", "5");
        val streamFromArray: Stream<String> = Arrays.stream(array);
        streamFromArray.parallel()
            .forEach(::println);
        println("");
        println("********************************************************")
        println("");

        //////////////////////////////////////////////////////////////
        // From Random numbers
        //////////////////////////////////////////////////////////////
        println("""
            **********************************************************
            Random number generators:
            
        """.trimIndent());
        val random = java.util.Random();
        val doubleStream: DoubleStream = random.doubles(10);
        val doubleStreamAverage = doubleStream.parallel()
            .peek(::println)
            .average()
            .asDouble;
        println("""
            Double Stream Average: $doubleStreamAverage
            ***********************************************************

        """.trimIndent());


        //////////////////////////////////////////////////////////////
        // From Concatening streams
        //////////////////////////////////////////////////////////////
        println("""
            **********************************************************
            Concatening streams:
            
        """.trimIndent());
        val stream1 = Stream.of("1", "2", "3", "4");
        val stream2 = Stream.of("5", "6", "7", "8");
        val finalStream = Stream.concat(stream1, stream2);
        finalStream.parallel()
            .forEach(::println);
        println("""
            
            **********************************************************
            
        """.trimIndent())

    }
}











