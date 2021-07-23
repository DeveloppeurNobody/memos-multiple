package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe05_custom_phaser_student_exercice

import java.util.*
import java.util.concurrent.Phaser
import java.util.concurrent.TimeUnit

/**
 * This class implements an student in the exam
 */
class Student(val phaser: Phaser) : Runnable {

    /**
     * Phaser to control the execution
     */
    // val phaser: Phaser

    /**
     * Main method of the student. It arrives to the exam and does three exercises. After each
     * exercise, it calls the phaser to wait that all the students finishes the same exercise.
     */
    override fun run() {
        println("${Thread.currentThread().name}: Has arrived to the exam. ${Date()}");

        phaser.arriveAndAwaitAdvance();
        println("${Thread.currentThread().name}: Is going to do the first exercise. ${Date()}");
        doExercise();
        println("${Thread.currentThread().name}: Has done the first exercise. ${Date()}");

        phaser.arriveAndAwaitAdvance();
        println("${Thread.currentThread().name}: Is going to do the second exercise. ${Date()}");
        doExercise();
        println("${Thread.currentThread().name}: has finished the exam. ${Date()}");

        phaser.arriveAndAwaitAdvance();
    }

    private fun doExercise() {
        try {
            val duration: Long = (Math.random() * 10).toLong();
            TimeUnit.SECONDS.sleep(duration);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }
    }
}