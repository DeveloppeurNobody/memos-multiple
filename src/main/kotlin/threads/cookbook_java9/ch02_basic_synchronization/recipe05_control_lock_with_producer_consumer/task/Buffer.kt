package threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task

import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 * this class implements a buffer to stores the simulate file lines
 * between the producer and the consumers
 */
class Buffer(var maxSize: Int){
    val buffer: LinkedList<String> = LinkedList();
    val lock = ReentrantLock();
    val lines = lock.newCondition();
    val space = lock.newCondition();
    private var pendingLines: Boolean = true;

    fun insert(line: String) {
        lock.lock();

        try {
            while (buffer.size == maxSize) {
                space.await();
            }
            buffer.offer(line);
            println("${Thread.currentThread().name}: Inserted Line: ${buffer.size}");
            lines.signalAll();
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        } finally {
            lock.unlock();
        }
    }

    fun get(): String {
        var line: String = "";
        lock.lock();
        try {
            while ((buffer.size == 0) && (hasPendingLines())) {
                lines.await();
            }

            if (hasPendingLines()) {
                line = buffer.poll();
                println("${Thread.currentThread().name}: Line Readed: ${buffer.size}");
                space.signalAll();
            }
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        } finally {
            lock.unlock();
        }
        return line;
    }

    @Synchronized fun setPendingLines(pendingLines: Boolean) {
        this.pendingLines = pendingLines;
    }

    @Synchronized fun hasPendingLines(): Boolean {
        return pendingLines || buffer.size > 0;
    }
}

