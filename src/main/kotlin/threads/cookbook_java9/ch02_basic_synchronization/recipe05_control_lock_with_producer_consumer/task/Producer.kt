package threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.task

import threads.cookbook_java9.ch02_basic_synchronization.recipe05_control_lock_with_producer_consumer.utils.FileMock

class Producer(var mock: FileMock,
               var buffer: Buffer) : Runnable {

    override fun run() {
        buffer.setPendingLines(true);
        while (mock.hasMoreLines()) {
            val line = mock.getLine();
            buffer.insert(line);
        }
        buffer.setPendingLines(false);
    }

}