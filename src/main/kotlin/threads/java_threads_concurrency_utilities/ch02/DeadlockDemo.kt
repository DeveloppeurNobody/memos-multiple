package threads.java_threads_concurrency_utilities.ch02

import java.lang.Exception

object DeadlockDemo {
    val lock1 = Any();
    val lock2 = Any();

    private fun instanceMethod1() {
        synchronized(lock1) {
            synchronized(lock2) {
                println("first thread in instanceMethod1");
                // critical section guarded first by
                // lock1 and then by lock2
            };
        };
    }

    private fun instanceMethod2() {
        synchronized(lock2) {
            synchronized(lock1) {
                println("second thread in instanceMethod2");
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val r1 = Runnable {
            while (true) {
                instanceMethod1();
                try {
                    Thread.sleep(50);
                } catch (ie: InterruptedException) {}
            }
        }

        val thdA = Thread(r1);

        val r2 = Runnable {
            while (true) {
                instanceMethod2();
                try {
                    Thread.sleep(50);
                } catch (e: Exception) {
                    System.err.println(e);
                }
            }
        }

        val thdB = Thread(r2);
        thdA.start();
      //  thdB.start()
    }
}