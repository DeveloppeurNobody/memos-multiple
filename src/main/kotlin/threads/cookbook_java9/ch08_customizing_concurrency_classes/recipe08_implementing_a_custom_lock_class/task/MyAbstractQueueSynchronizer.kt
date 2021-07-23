package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class.task

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.AbstractQueuedSynchronizer

/**
 * This class extends the AbstractQueueSynchronizer class to implement
 * the basis of a Lock. Internally, it uses an AtomicInteger variable
 * to store the state of the lock. It also stores the current thread that
 * has the lock. The tryAcquire()  method and tryRelease() method
 * are the starting point for the Lock implementation
 *
 */
class MyAbstractQueueSynchronizer : AbstractQueuedSynchronizer() {
    companion object {
        const val serialVersionUID = 1L;
    }

    private val state = AtomicInteger(10);

    override fun tryAcquire(arg: Int): Boolean = state.compareAndSet(0, 1);

    override fun tryRelease(arg: Int): Boolean = state.compareAndSet(1, 0);
}