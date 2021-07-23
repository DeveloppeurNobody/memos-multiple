package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe01_monitoring_a_lock_interface

import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe01_monitoring_a_lock_interface.lock.MyLock
import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe01_monitoring_a_lock_interface.lock.Task
import java.util.concurrent.TimeUnit

/**
 * Main class of the example. Create five threads to execute the task and write info
 * about the Lock shared by all the threads
 *
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a lock object
        val lock = MyLock();

        // Create an array for five threads
        val threads = arrayOfNulls<Thread>(5);

        repeat(5) {
            val task = Task(lock);
            threads[it] = Thread(task);
            threads[it]?.start();
        }

        // Create a loop with 15 steps
        repeat(15) {
            // Write info about the lock
            println("""
                Main: Logging the lock
                **********************
                Lock: Owner : ${lock.getOwnerName()}
                Lock: Queued Threads: ${lock.hasQueuedThreads()}
            """.trimIndent());

            if (lock.hasQueuedThreads()) {
                println("""
                    Lock: Queue Length: ${lock.queueLength}
                    Lock: Queue Threads: 
                """.trimIndent());
                val lockedThreads: Collection<Thread> = lock.getThreads();
                lockedThreads.map { ">>>> ${it.name}" }
                    .forEach(::println);
                println("\n");
            }

            println("""
                Lock: Fairness: ${lock.isFair}
                Lock: Locked: ${lock.isLocked}
            """.trimIndent());

            // Sleep the thread for one second
            TimeUnit.SECONDS.sleep(1);
        }
    }
}