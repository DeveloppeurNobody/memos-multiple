package threads.cookbook_java9.ch02_basic_synchronization.recipe04_synchronizing_data_access_with_reentrant_read_write_locks

import java.util.*

/**
 * This class implements a reader that consults the prices
 */
class Reader(var pricesInfo: PricesInfo) : Runnable {

    override fun run() {
        (0 until 20).forEach {
            println("""
                ${Date()}: ${Thread.currentThread().name}: Price1: ${pricesInfo.getPrice1()}
                ${Date()}: ${Thread.currentThread().name}: Price2: ${pricesInfo.getPrice2()}
            """.trimIndent());
        }
    }
}