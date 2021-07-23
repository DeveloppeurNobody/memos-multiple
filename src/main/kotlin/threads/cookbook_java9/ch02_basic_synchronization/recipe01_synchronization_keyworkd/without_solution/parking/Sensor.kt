package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.without_solution.parking

import java.util.concurrent.TimeUnit

class Sensor(var stats: ParkingStats) : Runnable {

    override fun run() {
        (0 until 10).forEach {
            stats.carComeIn();
            stats.carComeIn();

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }

            stats.motoComeIn();

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (ie: InterruptedException) {
              System.err.println(ie);
            }

            stats.motoGoOut();
            stats.carGoOut();
            stats.carGoOut();
        }
    }
}