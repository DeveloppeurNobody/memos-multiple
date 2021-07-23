package threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.StampedLock

class Reader (val position: Position,
              val lock: StampedLock) : Runnable {

    override fun run() {
        (0 until 50).forEach {
            val stamp = lock.readLock();

            try {
                println("Reader: $stamp - (${position.x},${position.y})");
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            } finally {
                lock.unlockRead(stamp);
                println("Reader: $stamp - Lock released");
            }
        }
    }
}