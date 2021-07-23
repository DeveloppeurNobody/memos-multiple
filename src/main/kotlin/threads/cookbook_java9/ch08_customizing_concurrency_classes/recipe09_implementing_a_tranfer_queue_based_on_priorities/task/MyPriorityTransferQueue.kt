package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe09_implementing_a_tranfer_queue_based_on_priorities.task

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.TransferQueue
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock

/**
 * This class implements a priority based transfer queue. It extends the
 * PriorityBlockingQueue class and implements the TransferQueue interface
 *
 * @param <E>
 *            Class of the elements to be stored in the queue
 */
class MyPriorityTransferQueue<E> : TransferQueue<E>, PriorityBlockingQueue<E>() {
    companion object {
        const val serialVersionUID = 1L;
    }

    // Numbers of consumers waiting
    val counter = AtomicInteger(0);

    // Lock to control access to the operations
    val lock = ReentrantLock();

    // Blocking queue to store the transfered elements
    val transfered = LinkedBlockingQueue<E>();

    override fun tryTransfer(e: E): Boolean {
        var value = false;
        value = try {
            lock.lock();
            if (counter.get() == 0) {
                false;
            } else {
                put(e);
                true;
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    /**
     * Transfer an element to the consumer. If there is a consumer waiting, puts
     * the element on the queue and return the true value. Else, puts the value
     * in the transfered queue and returns the false value. In this case, the
     * thread than makes the call will be blocked until a consumer takes the
     * transfered elements
     */
    @Throws(InterruptedException::class)
    override fun transfer(e: E) {
        lock.lock();
        if (counter.get() == 0) {
            try {
                put(e);
            } finally {
                lock.unlock();
            }
        } else {
            try {
                transfered.add(e);
            } finally {
                lock.unlock();
            }
            synchronized (this) {
                //e.wait();
            }
        }
    }

    /**
     * This method tries to transfer an element to a consumer waiting a maximum
     * period of time. If there is a consumer waiting, puts the element in the
     * queue. Else, puts the element in the queue of transfered elements and
     * wait the specified period of time until that time pass or the thread is
     * interrupted.
     */
    @Throws(InterruptedException::class)
    override fun tryTransfer(e: E, timeout: Long, unit: TimeUnit?): Boolean {
        lock.lock()
        return if (counter.get() != 0) {
            try {
                put(e)
            } finally {
                lock.unlock()
            }
            true
        } else {
            var newTimeout: Long = 0
            newTimeout = try {
                transfered.add(e)
                TimeUnit.MILLISECONDS.convert(timeout, unit)
            } finally {
                lock.unlock()
            }
         //   e.wait(newTimeout)
            lock.lock()
            val value: Boolean = try {
                if (transfered.contains(e)) {
                    transfered.remove(e)
                    false
                } else {
                    true
                }
            } finally {
                lock.unlock()
            }
            value
        }
    }

    /**
     * Method that returns if the queue has waiting consumers
     */
    override fun hasWaitingConsumer(): Boolean {
        return counter.get() != 0
    }

    /**
     * Method that returns the number of waiting consumers
     */
    override fun getWaitingConsumerCount(): Int {
        return counter.get()
    }

    /**
     * Method that returns the first element of the queue or is blocked if the
     * queue is empty. If there is transfered elements, takes the first
     * transfered element and wake up the thread that is waiting for the
     * transfer of that element. Else, takes the first element of the queue or
     * is blocked until there is one element in the queue.
     */
    @Throws(InterruptedException::class)
    override fun take(): E? {
        lock.lock()
        var value: E? = null
        try {
            counter.incrementAndGet()
            value = transfered.poll()
            if (value == null) {
                lock.unlock()
                value = super.take()
                lock.lock()
            } else {
                //synchronized(value) { value.notify() }
            }
            counter.decrementAndGet()
        } finally {
            lock.unlock()
        }
        return value
    }

}