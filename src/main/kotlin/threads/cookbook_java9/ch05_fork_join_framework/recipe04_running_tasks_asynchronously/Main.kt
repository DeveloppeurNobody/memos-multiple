package threads.cookbook_java9.ch05_fork_join_framework.recipe04_running_tasks_asynchronously

import threads.cookbook_java9.ch05_fork_join_framework.recipe04_running_tasks_asynchronously.task.FolderProcessor
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Create the pool
        val pool = ForkJoinPool();

        // Create three FolderProcessor tasks for three different folders
        val system = FolderProcessor("/var/log", "log");
        val apps = FolderProcessor("/home/ryu", "log");
        val documents = FolderProcessor("/home/ryu/Documents", "log");

        // Execute the three tasks in the pool
        pool.execute(system);
        pool.execute(apps);
        pool.execute(documents);

        // Write statistics of the pool until the three tasks end
        do {
            println("""
                *************************************************
                Main: Parallelism: ${pool.parallelism}
                Main: Active Threads: ${pool.activeThreadCount}
                Main: Task Count: ${pool.queuedTaskCount}
                Main: Steal Count: ${pool.stealCount}
                Main: ${system.isDone} - ${apps.isDone} - ${documents.isDone}
                Main: ${system.pendingCount} - ${apps.pendingCount} - ${documents.pendingCount}
                *************************************************
            """.trimIndent());

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (ie: InterruptedException) {
                System.err.println(ie);
            }
        } while ((!system.isDone) || (!apps.isDone) || (!documents.isDone));

        // Shutdown the pool
        pool.shutdown();

        // Write the number of results calculate by each task
        var results: MutableList<String>;

        results = system.getResultList();
        println("System: ${results.size}");

        results = apps.getResultList();
        println("Apps: ${results.size}");

        println("Documents: ${documents.getResultList().size}")
    }
}