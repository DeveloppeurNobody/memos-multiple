package threads.cookbook_java9.ch02_basic_synchronization.recipe04_synchronizing_data_access_with_reentrant_read_write_locks

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * This class simulates the store of two prices.
 * We will have a writer that stores the prices
 * and readers that consult this prices
 */
class PricesInfo {
    /**
     * two prices
     */
    private var price1: Double = 1.0
    private var price2: Double = 2.0;
    val lock: ReadWriteLock = ReentrantReadWriteLock();

    /**
     * Returns the first price
     */
    fun getPrice1(): Double {
        lock.readLock().lock();
        val value = price1;
        lock.readLock().unlock();
        return value;
    }

    /**
     * Returns the seconds price
     */
    fun getPrice2(): Double {
        lock.readLock().lock();
        val value = price2;
        lock.readLock().unlock();
        return value;
    }

    /**
     * Establish the prices
     * @param price1 The price of the fist product
     * @param price2 The price of the second product
     */
    fun setPrices(price1: Double, price2: Double) {
        lock.writeLock().lock();
        println("${Date()}: PricesInfo Write Lock Acquired.");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        this.price1 = price1;
        this.price2 = price2;
        println("${Date()}: PricesInfo: Write Lock Released.");
        lock.writeLock().unlock();
    }

}