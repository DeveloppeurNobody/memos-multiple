package threads.cookbook_java9.ch05_fork_join_framework.recipe06_cancel_tasks_before_executing.util

/**
 * Class that generates an array of integer numbers between 0 and 10
 * with a size specified as parameter
 *
 */
class ArrayGenerator {
    /**
     * Method that generates an array of integer numbers between 0 and 10
     * with the specified size
     * @param size The size of the array
     * @return An array of random integer numbers between 0 and 10
     */
    fun generateArray(size: Int): Array<Int?> {
        val array = arrayOfNulls<Int>(size);
        val random = java.util.Random();
        (array.indices).forEach {
            array[it] = random.nextInt(10);
        }

        return array;
    }
}

