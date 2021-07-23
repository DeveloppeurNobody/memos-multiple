package threads.cookbook_java9.ch01_thread_management.recipe09_thread_group_with_handler_of_exception.group

class MyThreadGroup(name: String) : ThreadGroup(name) {
    /**
     * Constructor of the class. Calls the parent class constructor
     * name of threadGroup is mandatory
     */

    /**
     * method to process the uncaught exception
     */
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        // Prints the name of the Thread
        println("The thread ${t?.id} has thrown an Exception");

        // Prints the stack trace of the exception
        e?.printStackTrace();

        // Interrupt the rest of the threads of the thread group
        println("Terminating the rest of the Threads");
        interrupt();
    }
}