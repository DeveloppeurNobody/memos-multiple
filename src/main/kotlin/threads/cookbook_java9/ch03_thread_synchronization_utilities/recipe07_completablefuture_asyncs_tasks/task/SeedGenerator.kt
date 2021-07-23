package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.math.round

class SeedGenerator(var resultCommunicator: CompletableFuture<Int>) : Runnable {

    override fun run() {
        println("SeedGenerator: Generating seed...");

        // Wait 5 seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (ie: InterruptedException) {
          System.err.println(ie);
        }

        val seed: Int = round(Math.random() * 10).toInt();

        println("SeedGenerator: Seed generated: $seed");
        resultCommunicator.complete(seed);
    }
}