package threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks.task

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * this class implements a concurrent task
 */
class Task(val name: String = "",
           private val initDate: Date = Date()) : Runnable {

    override fun run() {
        println("${Thread.currentThread().name}: Task $name: Created on: $initDate");
        println("${Thread.currentThread().name}: Task $name: Started on: ${Date()}");

        try {
            val duration = (Math.random() * 10).toLong();
            println("${Thread.currentThread().name}: Task: $name: Doing a task during $duration seconds");
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        println("${Thread.currentThread().name}: Task: $name: Finished on ${Date()}");
    }
}