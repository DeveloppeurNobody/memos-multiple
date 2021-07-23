package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe02_monitoring_a_phaser_class.phaser

import java.util.concurrent.Phaser
import java.util.concurrent.TimeUnit


/**
 * Task to write information about a Phaser. It executes three phases. In each
 * phase, it sleeps the thread the number of seconds specified by the time attribute
 *
 *
 * Constructor of the class. Initialize its attributes
 * @param time Number of seconds this task is going to sleep the thread in each phase
 * @param phaser Phaser to synchronize the execution of tasks
*/
class Task(val time: Long,
           val phaser: Phaser) : Runnable {

    override fun run() {
        // Arrive to the phaser
        phaser.arrive();

        // Phase 1
        println("${Thread.currentThread().name}: Entering phase 1.");
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println("${Thread.currentThread().name}: Finishing phase 1.");

        // End of Phase 1
        phaser.arriveAndAwaitAdvance();

        // Phase 2
        println("${Thread.currentThread().name}: Entering phase 2.");
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println("${Thread.currentThread().name}: Finishing phase 2.");

        // End of phase 2
        phaser.arriveAndAwaitAdvance();

        // Phase 3
        println("${Thread.currentThread().name}: Entering phase 3.");
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (ie: InterruptedException) {
            System.err.println(ie);
        }
        println("${Thread.currentThread().name}: Finishing phase 3.");

        // End of phase 3
        phaser.arriveAndDeregister();

    }
}