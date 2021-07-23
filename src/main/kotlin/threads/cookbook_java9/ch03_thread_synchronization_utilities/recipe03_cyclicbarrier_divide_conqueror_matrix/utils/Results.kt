package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils

/**
 * This class is used to store the number of occurrences of the number
 * we are looking for in each row of the bi-dimensional array
 */
data class Results(var size: Int) {
    var data = arrayOfNulls<Int>(size);

    fun setData(position: Int, value: Int) {
        data[position] = value;
    }
}