package threads.cookbook_java9.ch01_thread_management.recipe07_caught_exception_in_threads.task

/**
 * Runnable class that throws an Exception
 */
class Task : Runnable {
    override fun run() {
        val number: Int = "TTT".toInt();

        // This sentence will never be executed
        println("Number: $number");
    }
}
