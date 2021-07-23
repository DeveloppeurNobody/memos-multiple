package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task

class Sensor1 (val counter: ParkingCounter) : Runnable {
    override fun run() {
        repeat(4) {
            counter.carIn();
        }

        repeat(3) {
            counter.carOut();
        }

        repeat(3) {
            counter.carIn();
        }
    }
}