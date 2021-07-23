package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution

import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking.ParkingCash
import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking.ParkingStats
import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking.Sensor

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        var cash = ParkingCash();
        var stats = ParkingStats(cash);

        println("Parking Simulator");
        val numberSensors = 2 * Runtime.getRuntime().availableProcessors();
        val threads = arrayOfNulls<Thread>(numberSensors);

        (0 until numberSensors).forEach {
            val thread = Thread(Sensor(stats));
            thread.start();
            threads[it] = thread;
        }

        (0 until numberSensors).forEach {
            try {
                threads[it]?.join();
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }
        }

        println("Number of cars: ${stats.numberCars}");
        println("Number of motorcycles: ${stats.numberMotorcycles}");

        cash.close();

    }
}