package threads.cookbook_java9.ch08_customizing_concurrency_classes.recipe10_implementing_your_own_object_atomic_object.task

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max

class ParkingCounter (private val maxNumber: Int) : AtomicInteger() {
    companion object {
        const val serialVersionUID = 1L;
    }

    init {
        set(0);
    }

    /**
     * Method that increments the internal counter if it has
     * a value less than the maximum. Is implemented to be and
     * atomic operation
     * @return True if the car can enter in the parking, false if not.
     */
    fun carIn(): Boolean {
        while (true) {
            var value = get();
            if (value == maxNumber) {
                println("ParkingCounter: The parking is full.\n");
            } else {
                var newValue = value + 1;
                var changed = compareAndSet(value, newValue);
                if (changed) {
                    println("ParkingCounter: A car has entered.\n");
                    return true;
                }
            }
        }
    }

    /**
     * Method that decrements the internal counter if it has
     * a value bigger than 0. Is implemented to be and
     * atomic operation
     * @return True if the car leave the parking, false if there are 0 cars
     * in the parking
     */
    fun carOut(): Boolean {
        while (true) {
            var value = get();
            if (value == 0) {
                println("ParkingCounter: The parking is empty\n");
                return false;
            } else {
                var newValue = value - 1;
                var changed = compareAndSet(value, newValue);
                if (changed) {
                    println("ParkingCounter: A car has gone out.\n");
                    return true;
                }
            }
        }
    }


    override fun toByte(): Byte = maxNumber.toByte();

    override fun toChar(): Char = maxNumber.toChar();

    override fun toShort(): Short = maxNumber.toShort();
}
