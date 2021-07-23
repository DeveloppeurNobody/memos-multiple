package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class.task

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.AbstractQueuedSynchronizer
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

/**
 * This class implements a basic Lock. It uses a myAbstractQueueSyncrhonized object
 * as the element from which implement the methods of the lock.
 *
 */
class MyLock : Lock {
    val sync: AbstractQueuedSynchronizer = MyAbstractQueueSynchronizer();

    /**
     * Method that try to acquire the lock. If it can't, the thread
     * will be blocked until the thread that has it release the lock
     */
    override fun lock() = sync.acquire(1);

    /**
     * Method that try to acquire the lock. If it can't, the thread will
     * be blocked until the thread that has it release the lock. The difference
     * with the lock() method is that in this case, the blocked threads can
     * be interrupted
     */
    @Throws(InterruptedException::class)
    override fun lockInterruptibly() = sync.acquireInterruptibly(1);

    /**
     * Method that try to acquire the lock. If it can, the method returns the true
     * value. It it can't, the method return the false value
     */
    override fun tryLock(): Boolean {
        return try {
            sync.tryAcquireNanos(1, 1000);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
            false;
        }
    }

    /**
     * Method that try to acquire the lock. If it can, the method returns the true value.
     * If it can't, wait the time specified as parameter and if the lock hasn't been
     * released, it returns the false value. It the lock is released in that period of time,
     * the thread acquires the lock and the method returns the true value
     */
    @Throws(InterruptedException::class)
    override fun tryLock(time: Long, unit: TimeUnit): Boolean = sync.tryAcquireNanos(1, TimeUnit.NANOSECONDS.convert(time, unit));

    /**
     * Method that release the lock
     */
    override fun unlock() {
        sync.release(1);
    }

    /**
     * Method that creates a new condition for the lock
     */
    override fun newCondition(): Condition = sync.ConditionObject();
}