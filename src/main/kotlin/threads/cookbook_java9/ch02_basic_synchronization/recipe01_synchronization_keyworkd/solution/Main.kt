package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution

import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking.Sensor
import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking.ParkingCash
import threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking.ParkingStats


object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val cash = ParkingCash()
        val stats = ParkingStats(cash)
        System.out.printf("Parking Simulator\n")
        val numberSensors = 2 * Runtime.getRuntime().availableProcessors()
        val threads = arrayOfNulls<Thread>(numberSensors)
        for (i in 0 until numberSensors) {
            val sensor = Sensor(stats)
            val thread = Thread(sensor)
            thread.start()
            threads[i] = thread
        }
        for (i in 0 until numberSensors) {
            try {
                threads[i]!!.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        System.out.printf("Number of cars: %d\n", stats.getNumberCars())
        System.out.printf("Number of motorcycles: %d\n", stats.getNumberMotorcycles())
        cash.close()
    }
}