package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils

import java.util.*

class MatrixMock(val size: Int,
                 val length: Int,
                 val number: Int) {

    val data = Array(size) { Array<Int>(length) { -1 } };

    init {
        var counter: Int = 0;
        val random = Random();
        repeat(size) {i ->
            repeat(length) {j ->
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number) {
                    counter++;
                }
            }
        }

        println("Mock There are $counter occurences of number $number in generated data.");
    }

    fun getRow(row: Int): Array<Int> {
        return if ((row >= 0) && (row < data.size)) {
            data[row];
        } else {
            emptyArray();
        }
    }
}