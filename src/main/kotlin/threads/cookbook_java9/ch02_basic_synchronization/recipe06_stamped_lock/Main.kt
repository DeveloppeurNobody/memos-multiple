package threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock

import threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task.OptimisticReader
import threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task.Position
import threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task.Reader
import threads.cookbook_java9.ch02_basic_synchronization.recipe06_stamped_lock.task.Writer
import java.util.concurrent.locks.StampedLock


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val position = Position();
        val lock = StampedLock();

        val threadWriter = Thread(Writer(position, lock));
        val threadReader = Thread(Reader(position, lock));
        val threadOptReader = Thread(OptimisticReader(position, lock));

        threadWriter.start();
        threadReader.start();
        threadOptReader.start();

        try {
            threadWriter.join();
            threadReader.join();
            threadOptReader.join();
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }
}