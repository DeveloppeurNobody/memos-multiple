import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {

    // FIRST EXAMPLE
    println("Starting a coroutine block...");

    // 3 coroutines => 1 runBlocking and 2 launch
    runBlocking {
        println("Coroutine block started");

        launch {
            println(" 1/ First coroutine start");
            delay(100);
            println(" 1/ First coroutine end");
        }

        launch {
            println(" 2/ Second coroutine start");
            delay(50);
            println(" 2/ Second coroutine end");
        }
        println("Two coroutines have been launched");
    }
    println("Back from coroutine block");

    // SECOND EXAMPLE NON-CONCURRENCY
    runBlocking {

        launch {
            println("First coroutine start, suspend for 50ms");
            delay(50);
            println("First coroutine : starting some computation for 100ms");
            val t0 = System.currentTimeMillis();
            while (System.currentTimeMillis() - t0 < 100) {
                // Simulate a computation taking 100ms by wasting
                // CPU cycles in this loop
                // We could also use Thread.sleep()
            }
            println("Computation ended");
        }

        launch {
            println("Second coroutine start, suspend for 100ms");
            val time = measureTimeMillis { delay(100) }
            println("Second coroutine end after ${time}ms");
        }
    }



}