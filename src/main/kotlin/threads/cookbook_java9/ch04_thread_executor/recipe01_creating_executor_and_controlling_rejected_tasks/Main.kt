package threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks

import threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks.task.Server
import threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks.task.Task

/**
 * Main class of the example. Creates a server and 100 request of the Task class
 * that sends to the server
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Creates the server
        val server = Server();

        // Send 100 request to the server and finish
        println("Main: Starting");
        repeat(100) {
            val task = Task("Task $it");
            server.executeTask(task);
        }

        // Shutdown the executor
        println("Main: Shuting down the Executor.");
        server.endServer();

        // Send a new task. This task will be rejected
        println("Main: Sending another Task");
        val task = Task("Rejected Task");
        server.executeTask(task);

        println("Main: End.");
    }
}