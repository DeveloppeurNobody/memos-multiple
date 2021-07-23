package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe08_implementing_a_custom_lock_class.task

import java.util.concurrent.TimeUnit

class Task(val name: String,
           val lock: MyLock) : Runnable {

    override fun run() {
        lock.lock();
        println("Task: $name: Take the lock\n");
        try {
            TimeUnit.SECONDS.sleep(1);
            println("Task: $name: Free the lock\n");
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        } finally {
            lock.unlock();
        }
    }
}
