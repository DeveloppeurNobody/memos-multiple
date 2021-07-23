package threads.cookbook_java9.ch09_testing_concurrent_applications.recipe02_monitoring_a_phaser_class

import threads.cookbook_java9.ch09_testing_concurrent_applications.recipe02_monitoring_a_phaser_class.phaser.Task
import java.util.concurrent.Phaser
import java.util.concurrent.TimeUnit

/**
 * Main class of the example. Creates a Phaser with three participants and
 * Three task objects. Write information about the evolution of the Phaser
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create a new Phaser for three participants
        val phaser = Phaser(3);

        // Create and launch three tasks
        repeat(3) {
            val task = Task((it + 1).toLong(), phaser);
            val thread = Thread(task);
            thread.start();
        }

        // Write information about the phaser
        repeat(10) {
            println("""
                **************************************************************
                Main: Phaser Log
                Main: Phaser: Phaser: ${phaser.phase}
                Main: Phaser: Registered Parties: ${phaser.registeredParties}
                Main: Phaser: Arrived Parties: ${phaser.arrivedParties}
                Main: Phaser: Unarrived Parties: ${phaser.unarrivedParties}
                **************************************************************
            """.trimIndent());

            TimeUnit.SECONDS.sleep(1);
        }
    }
}