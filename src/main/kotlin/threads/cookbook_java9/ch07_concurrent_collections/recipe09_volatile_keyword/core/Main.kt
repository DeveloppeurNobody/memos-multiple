package threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.core

import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.data.Flag
import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.data.VolatileFlag
import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.task.Task
import threads.cookbook_java9.ch07_concurrent_collections.recipe09_volatile_keyword.task.VolatileTask
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
    val volatileFlag = VolatileFlag();
    val flag = Flag();

    val volatileTask = VolatileTask(volatileFlag);
    val task = Task(flag);

    var thread = Thread(volatileTask);
    thread.start();
    thread = Thread(task);
    thread.start();

    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    println("Main: Going to stop volatile task: ${Date()}");
    volatileFlag.flag = false;
    println("Main: Volatile stop flag changed: ${Date()}");

    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }

    println("Main: Going to stop task: ${Date()}");
    flag.flag = false;
    println("Main: Task stoped: ${Date()}");

    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (ie: InterruptedException) {
        System.err.println(ie);
    }
}