package threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.StampedLock


class OptimisticReader(val position: Position,
                       val lock: StampedLock) : Runnable {

    override fun run() {
        var stamp: Long;

        (0 until 100).forEach {
            try {
                stamp = lock.tryOptimisticRead();
                val x = position.x;
                val y = position.y;

                if (lock.validate(stamp)) {
                    println("OptimisticReader: $stamp ($x,$y)");
                } else {
                    println("OptimisticReader: $stamp - Not Free");
                }
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}

