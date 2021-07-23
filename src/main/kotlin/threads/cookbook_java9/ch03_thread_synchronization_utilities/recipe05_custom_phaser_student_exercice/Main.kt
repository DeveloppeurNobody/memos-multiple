package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe05_custom_phaser_student_exercice

object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        // Creates the phaser
        val phaser = MyPhaser();

        // Creates 5 students and register them in the phaser
        val students = arrayOfNulls<Student>(5);
        repeat(students.size) {
            students[it] = Student(phaser);
            phaser.register();
        }

        // Creates 5 threads for the students and start them
        val threads = arrayOfNulls<Thread>(students.size);
        repeat(students.size) {
            threads[it] = Thread(students[it], "Student $it");
            threads[it]?.start();
        }

        threads.forEach {
            try {
              it?.join();
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }

        // Check that the Phaser is in the Terminated state.
        println("Main: The phaser has finished: ${phaser.isTerminated}");
    }
}