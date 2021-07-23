package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task

class Sensor2 (val counter: ParkingCounter) : Runnable {

    override fun run() {
        counter.carIn();

        repeat(2) {
            counter.carOut();
        }

        repeat(6) {
            counter.carIn();
        }
    }
}