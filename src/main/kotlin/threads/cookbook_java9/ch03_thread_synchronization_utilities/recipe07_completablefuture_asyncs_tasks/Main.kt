package threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks

import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task.NumberListGenerator
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task.NumberSelector
import threads.cookbook_java9.ch03_thread_synchronization_utilities.recipe07_completablefuture_asyncs_tasks.task.SeedGenerator
import java.util.concurrent.CompletableFuture
import kotlin.math.abs


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Main : Start");
        val seedFuture: CompletableFuture<Int> = CompletableFuture();
        val seedThread = Thread(SeedGenerator(seedFuture));
        seedThread.start();

        println("Main : Getting the seed");
        var seed = 0;
        try {
            seed = seedFuture.get();
        } catch (ie: Exception) {
          System.err.println(ie);
        }
        println("Main : The seed is $seed");

        println("Main : Launching the list of numbers generator");
        val task = NumberListGenerator(seed);
        val startFuture: CompletableFuture<MutableList<Long>> = CompletableFuture.supplyAsync(task);

        println("Main : Launching step 1");
        val step1Future: CompletableFuture<Long> = startFuture.thenApplyAsync { list ->
            println("${Thread.currentThread().name} : Step 1: Start");
            var selected = 0L;
            var selectedDistance = Long.MAX_VALUE;
            var distance: Long;
            list.forEach {number ->
                distance = abs( number - 1000);
                if (distance < selectedDistance) {
                    selected = number;
                    selectedDistance = distance;
                }
            }
            println("${Thread.currentThread().name} : Step 1 : Result - $selected");
            selected;
        }

        println("Main : Launching step 2");
        val step2Future: CompletableFuture<Long> = startFuture.thenApplyAsync { list ->
            list.stream()
                .max(Long::compareTo)
                .get()
        }

        val write2Future: CompletableFuture<Void> = step2Future.thenAccept { selected ->
            println("${Thread.currentThread().name}: Step 2: Result - $selected");
        }

        println("Main : Launching step 3");
        val numberSelector = NumberSelector();
        val step3Future: CompletableFuture<Long> = startFuture.thenApplyAsync(numberSelector);

        println("Main : Wainting for the end of the three steps");
        val waitFuture: CompletableFuture<Void> = CompletableFuture.allOf(step1Future, write2Future, step3Future);
        val finalFuture: CompletableFuture<Void> = waitFuture.thenAcceptAsync { param ->
            println("Main: The CompletableFuture example has been completed.");
        }

        finalFuture.join();

    }
}
