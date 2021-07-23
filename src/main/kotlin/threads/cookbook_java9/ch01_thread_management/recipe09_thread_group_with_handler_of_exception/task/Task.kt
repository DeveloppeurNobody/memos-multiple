package threads.cookbook_java9.ch01_thread_management.recipe09_thread_group_with_handler_of_exception.task

import kotlin.random.Random

class Task : Runnable {
    override fun run() {
        var result: Int
        // Create a random number generator
        val random = Random(Thread.currentThread().id)
        while (true) {
            // Generate a random number and calculate 1000000000 divide by that random number
            result = 1000 / (random.nextDouble() * 1000000000).toInt()
            // Check if the Thread has been interrupted
            if (Thread.currentThread().isInterrupted) {
                System.out.printf("%d : Interrupted\n", Thread.currentThread().id)
                return
            }
        }
    }
}