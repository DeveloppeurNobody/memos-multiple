package threads.cookbook_java9.ch02_basic_synchronization.recipe01_synchronization_keyworkd.solution.parking

import java.util.concurrent.TimeUnit


class Sensor(private val stats: ParkingStats) :
    Runnable {
    override fun run() {
        for (i in 0..9) {
            stats.carComeIn()
            stats.carComeIn()
            try {
                TimeUnit.MILLISECONDS.sleep(50)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            stats.motoComeIn()
            try {
                TimeUnit.MILLISECONDS.sleep(50)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            stats.motoGoOut()
            stats.carGoOut()
            stats.carGoOut()
        }
    }

}
