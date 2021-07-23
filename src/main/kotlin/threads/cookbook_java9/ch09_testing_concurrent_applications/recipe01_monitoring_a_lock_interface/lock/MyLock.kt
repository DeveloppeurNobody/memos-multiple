package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe01_monitoring_a_lock_interface.lock

import java.util.concurrent.locks.ReentrantLock

/**
 * Create a specific class to extend the ReentrantLock class.
 * The main objective of this class is to convert in public two
 * protected methods.
 * getOwnerName() returns the name of the thread that have the control
 * of the lock and uses the proctected method getOwner();
 * getThreads() returns the list of threads queued in the lock and uses
 * the protected method getQueuedThreads();
 */
class MyLock : ReentrantLock() {
    companion object {
        const val serialVersionUID = 1L;
    }

    /**
     * This method returns the name of the thread that have the control of the Lock of the constant "None"
     * if the Lock is free
     * @return The name of the thread that has the control of the lock
     */
    fun getOwnerName(): String {
        if (this.owner == null) {
            return "None";
        }
        return this.owner.name;
    }

    /**
     * This method returns the list of the threads queued in the lock
     * @return The list of threads queued in the Lock
     */
    fun getThreads(): Collection<Thread> = this.queuedThreads;

}

