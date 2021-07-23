package threads.cookbook_java9.ch01_thread_management.recipe02_interrupts_thread.task

/**
 * This class generates prime numbers until is interrupted
 */
class PrimeGenerator : Thread() {
    override fun run() {
        var number = 1L;

        // this loop never ends... until is interrupted
        while (true) {
            if (isPrime(number)) {
                println("Number $number is Prime");
            }

            // When is interrupted, write a message and ends
            if (isInterrupted) {
                println("The Prime Generator has been interrupted");
                return;
            }

            number++;
        }
    }

    /**
     * Method that calculate if a number is prime or not
     * @param number The number
     * @return A boolean value. True if the number is prime, false if not
     */
    private fun isPrime(number: Long): Boolean {
        if (number <= 2) {
            return true;
        }

        (2 until number).forEach {
            if (number % it == 0L) {
                return false;
            }
        }

        return true;
    }
}