package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.task

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe03_cyclicbarrier_divide_conqueror_matrix.utils.Results

class Grouper(var results: Results) : Runnable {

    override fun run() {
        var finalResult = 0;
        println("Grouper: Processing results...");
        var data = results.data;
        data.forEach {
            finalResult += it?:-1;
        }
        println("Grouper: Total result: $finalResult");
    }
}

