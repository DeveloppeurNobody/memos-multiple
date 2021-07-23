package threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.task

import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.data.VolatileFlag
import java.util.*

class VolatileTask (val flag: VolatileFlag) : Runnable {

    override fun run() {
        var i = 0;
        while (flag.flag) {
            i++;
        }

        println("VolatileTask: Stopped $i - ${Date()}");
    }
}