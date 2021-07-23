package threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.StampedLock

class Writer(val position: Position,
             val lock: StampedLock) : Runnable {


    override fun run() {
        (0 until 10).forEach {
            val stamp = lock.writeLock();

            try {
                println("Writer: Lock acquired $stamp");
                position.x = position.x + 1;
                position.y = position.y + 1;
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            } finally {
                lock.unlockWrite(stamp);
                println("Writer: Lock released $stamp");
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}