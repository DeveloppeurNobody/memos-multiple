package threads.java_threads_concurrency_utilities.ch06_synchronizers

import java.lang.Exception
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * A countdown latch causes one or more threads to wait at a “gate” until another
 * thread opens this gate, at which point these other threads can continue.
 * It consists of a count and operations for “causing a thread to wait until the count reaches zero”
 * and “decrementing the count.”
 */
object CountDownLatchDemo {
    val NTHREADS: Int = 3;

    @JvmStatic
    fun main(args: Array<String>) {
        var startSignal: CountDownLatch = CountDownLatch(1);
        var doneSignal: CountDownLatch = CountDownLatch(NTHREADS);

        var r: Runnable = object : Runnable {
            override fun run() {
                try {
                    report("entered run()");

                    // wait until told to ...
                    startSignal.await();

                    report("doing work");

                    Thread.sleep((Math.random() * 1000).toLong());

                    // reduce count on wich main thread is waiting...
                    doneSignal.countDown();
                } catch (e: Exception) {
                    System.err.println(e);
                }
            }

            fun report(s: String) {
                println("${System.currentTimeMillis()} : ${Thread.currentThread()} : $s");
            }
        }

        var executor: ExecutorService = Executors.newFixedThreadPool(NTHREADS);
        for (i in 0 until NTHREADS) {
            executor.execute(r);
        }

        try {
            println("main thread doing something");
            Thread.sleep(1000);

            // let all threads proceed.
            println("main thread doing something else ");

            // wait for all threads to finish.
            executor.shutdownNow();
            doneSignal.await();
        } catch (e: Exception) {
            System.err.println(e);
        }
    }
}