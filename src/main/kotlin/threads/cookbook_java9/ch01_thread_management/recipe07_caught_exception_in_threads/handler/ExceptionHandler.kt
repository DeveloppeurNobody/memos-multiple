package threads.cookbook_java9.ch01_thread_management.recipe07_caught_exception_in_threads.handler

/**
 * Class that process the uncaught exceptions throwed in a thread
 */
class ExceptionHandler : Thread.UncaughtExceptionHandler {

    /**
     * Main method of the class. It process the uncaught exceptions throwed
     * in a thread
     *
     * @param t The thread that throws the Exception
     * @param e The Exception throwed
     */
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        println("""
            An exception has been captured
            Thread: ${t?.id}
            Exception: $e : message: ${e?.message}
            Stack Trace: 
        """.trimIndent());
        e?.printStackTrace(System.out);
        println("The thread status: ${t?.state}")

    }
}