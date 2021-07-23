package threads.cookbook_java9.ch01_thread_management.recipe01_ten_threads.task

/**
 * This classes prints the multiplication table of a number
 */
class Calculator : Runnable{

    /**
     * method that do the calculation
     */
    override fun run() {
        var current = 1L;
        var max = 20000L;
        var numPrimes = 0L;

        println("Thread '${Thread.currentThread().name}': START");
        while (current <= max) {
            if (isPrime(current)) {
                numPrimes++;
            }
            current++;
        }
        println("Thread '${Thread.currentThread().name}': END. Number of Primes: $numPrimes");
    }

    /**
     * Method that calculate if a number is prime or not
     * @param number: The number
     * @return A boolean value. True if the number is prime, false if not.
     */
    private fun isPrime(number: Long): Boolean {
        if (number <= 2) {
            return true;
        }

        (2 until number).forEach { i ->
            if ((number % i) == 0L) {
                return false;
            }
        }

        return true;
    }
}