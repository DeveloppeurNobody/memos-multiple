package threads.cookbook_java9.ch02_basic_synchronization.recipe04_synchronizing_data_access_with_reentrant_read_write_locks

import java.util.*

/**
 * This class implements a writer that establish the prices
 */
class Writer(var pricesInfo: PricesInfo) : Runnable {

    /**
     * Core method of the writer. Establish the prices
     */
    override fun run() {
        (0 until 3).forEach {
            println("${Date()}: Writer: Attempt to modify the prices");
            pricesInfo.setPrices((Math.random() * 10), (Math.random() * 8));
            println("${Date()}: Writer: Prices have been modified");
            try {
                Thread.sleep(2);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }
    }
}