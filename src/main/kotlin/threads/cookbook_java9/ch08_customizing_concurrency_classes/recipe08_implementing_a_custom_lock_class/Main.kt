package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class.task.MyLock
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class.task.Task
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new MyLock object
        val lock = MyLock();

        // Create and run ten tasks objects

            val task = Task("Task-1", lock);
            val thread = Thread(task);
            thread.start();


        // The main thread also tries to get to lock
        var value: Boolean = false;
        do {
            try {
                value = lock.tryLock(1, TimeUnit.SECONDS);
                if (!value) {
                    println("Main: Trying to get the Lock\n");
                }
            } catch (ie: InterruptedException) {
                System.err.println(ie);
                value = false;
            }
        } while (!value);

        // The main thread release the lock
        println("Main: Got the lock");
        lock.unlock();

        // Write a message in the console indicating the end of program.
        println("Main: End of the program.")
    }
}