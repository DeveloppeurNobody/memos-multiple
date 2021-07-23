package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe01_monitoring_a_lock_interface.lock

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock

class Task (val lock: Lock) : Runnable {

    override fun run() {
        repeat(5) {
            // Acquire lock
            lock.lock();
            println("${Thread.currentThread().name}: Get the lock.\n");

            // Sleeps the thread 500 milliseconds
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                println("${Thread.currentThread().name}: Free the lock.\n");
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            } finally {
                // free the lock.
                lock.unlock();
            }
        }
    }
}