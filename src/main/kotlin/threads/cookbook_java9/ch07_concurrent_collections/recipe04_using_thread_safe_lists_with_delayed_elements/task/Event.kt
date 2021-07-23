package threads.cookbook_java9.ch07_concurrent_collections.recipe04_using_thread_safe_lists_with_delayed_elements.task

import java.util.*
import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

class Event(private val startDate: Date) : Delayed {

    override fun compareTo(other: Delayed?): Int {
        val result: Long = this.getDelay(TimeUnit.NANOSECONDS) - other?.getDelay(TimeUnit.NANOSECONDS)!!;
        return if (result < 0) {
            -1;
        } else if (result > 0) {
            1;
        } else {
            0;
        }
    }

    override fun getDelay(unit: TimeUnit): Long {
        val now = Date();
        val diff = startDate.time - now.time;
        return unit.convert(diff, TimeUnit.NANOSECONDS);
    }
}