package threads.java_threads_concurrency_utilities.ch06_synchronizers

import java.util.*
import java.util.concurrent.CyclicBarrier

/**
 * A cyclic barrier lets a set of threads wait for each other to reach a common barrier
 * point. The barrier is cyclic because it can be reused after the waiting threads are released.
 * This synchronizer is useful in applications involving a fixed-size party of threads that
 * must occasionally wait for each other.
 */
object CyclicBarrierDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        var matrix = Array(3) { FloatArray(3)}
        var counter: Int = 0;
        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                matrix[row][col] = counter++.toFloat();
            }
        }

        dump(matrix);
        println();
        var solver: Solver = Solver(matrix);
        println()
        dump(matrix);
    }

    fun dump(matrix: Array<FloatArray>){
        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                print("${matrix[row][col]} ");
                println();
            }
        }
    }
}

class Solver(var matrix: Array<FloatArray>? = null) {
    var N: Int = 0;
    lateinit var data: Array<FloatArray>;
    lateinit var barrier: CyclicBarrier;

    val obj = java.lang.Object();

    init {
        if (matrix != null) {
            data = matrix!!;
            N = matrix?.size!!;
            barrier = CyclicBarrier(N, Runnable {
                mergeRows();
            });

            for (i in 0 until N) {
                Thread(Worker(i)).start();
            }

            waitUntilDone();
        }
    }

    fun mergeRows() {
        println("merging");
        synchronized("abc") {
            obj.notify();
        }
    }

    fun waitUntilDone() {
        synchronized("abc") {
            try {
                println("main thread waiting")
                obj.wait();
                println("main thread notified")
            } catch (ie: InterruptedException) {
                println("main thread interrupted")
            }
        }
    }

    inner class Worker(var row: Int) : Runnable {
        var myRow: Int = 0;
        var done: Boolean = false;

        init {
            myRow = row;
        }

        fun processRow(myRow: Int) {
            println("Processing row: $myRow")
            for (i in 0 until N) {
                val f: Float = data[myRow][i];
                data[myRow][i] = f * 10;
            }
            done = true
        }

        override fun run() {
            while (!done) {
                processRow(myRow);

                try {
                    barrier.await();
                } catch (e: Exception) {
                    System.err.println(e);
                    return;
                }
            }
        }
    }
}