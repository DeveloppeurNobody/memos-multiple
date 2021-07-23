package threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task

import java.util.*

class Consumer(var buffer: Buffer) : Runnable {
    override fun run() {
        while (buffer.hasPendingLines()) {
            val line: String = buffer.get();
            proccessLine(line);
        }
    }

    private fun proccessLine(line: String) {
        try {
            val random = Random();
            Thread.sleep(random.nextInt(100).toLong());
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }


}