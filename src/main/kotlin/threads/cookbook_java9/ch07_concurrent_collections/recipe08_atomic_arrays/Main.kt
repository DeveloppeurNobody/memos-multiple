package threads.cookbook_java9.ch07_concurrent_collections.recipe08_atomic_arrays

import java.util.concurrent.atomic.AtomicIntegerArray
import java.util.stream.Stream

fun main() {
    val THREADS = 100;

    // Atomic array whose elements will be incremented and decremented
    val vector = AtomicIntegerArray(1000);

    // An incrementer task
    val incrementer = Incrementer(vector);

    // An decrementer task
    val decrementer = Decrementer(vector);

    // Create and execute 100 incrementer and 100 decrementer tasks
    val threadIncrementer = arrayOfNulls<Thread>(THREADS);
    val threadDecrementer = arrayOfNulls<Thread>(THREADS);

    repeat(THREADS) {
        threadIncrementer[it] = Thread(incrementer);
        threadDecrementer[it] = Thread(decrementer);

        threadIncrementer[it]?.start();
        threadDecrementer[it]?.start();
    }

    // Wait for the finalization of all the tasks
    repeat(THREADS) {
        try {
            threadIncrementer[it]?.join();
            threadDecrementer[it]?.join();
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
    }

    // Write the elements different from 0
    var errors = 0;

    (0 until vector.length()).forEach {
        if (vector.get(it) != 0) {
            println("Vector[$it]: ${vector.get(it)}");
            errors++;
        }
    }

    if (errors == 0) {
        println("No errors found");
    }

    println("Main: End of program.")



}