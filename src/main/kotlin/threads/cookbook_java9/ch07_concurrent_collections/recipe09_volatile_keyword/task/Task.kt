package threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.task

import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.data.Flag
import java.util.*

data class Task(val flag: Flag) : Runnable {

    override fun run() {
        var i = 0;
        while (flag.flag) {
            i++;
        }
        println("Task: $i - ${Date()}");
    }
}