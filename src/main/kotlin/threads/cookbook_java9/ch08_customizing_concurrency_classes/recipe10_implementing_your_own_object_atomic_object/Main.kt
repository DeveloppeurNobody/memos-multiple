package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object

import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task.ParkingCounter
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task.Sensor1
import threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task.Sensor2

object Main {

    @JvmStatic
    @Throws(Exception::class)
    fun main(args: Array<String>) {
        // Create a ParkingCounter object
        val counter = ParkingCounter(5);

        // Create and lauch two sensors
        val sensor1 = Sensor1(counter);
        val sensor2 = Sensor2(counter);

        val thread1 = Thread(sensor1);
        val thread2 = Thread(sensor2);

        thread1.start();
        thread2.start();

        // Wait for the finalization of the threads
        thread1.join();
        thread2.join();

        // Write in the console the number of cars in the parking
        println("Main: Number of cars: ${counter.get()}");

        // Write a message indicating the end of the program.
        println("Main: End of the program.\n");
    }
}