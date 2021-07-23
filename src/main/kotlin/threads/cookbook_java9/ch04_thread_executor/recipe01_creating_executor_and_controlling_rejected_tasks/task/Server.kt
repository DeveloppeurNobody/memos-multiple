package threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks.task

import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

/**
 * this class simulates a server, for example, a web server, that receives
 * requests and uses a ThreadPoolExecutor to execute those requests
 */
class Server {
    /**
     * Create the executor
     */
    private val executor: ThreadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()) as ThreadPoolExecutor;

    init {
        // Create the contoller for the Rejected tasks
        val controller: RejectedTaskController = RejectedTaskController();
        // Establish the rejected task controller
        executor.rejectedExecutionHandler = controller;
    }

    /**
     * this method is called when a request to the server is made.
     * The server uses the executor to execute the request that it receives
     *
     * @param task The request made to the server
     */
    fun executeTask(task: Task) {
        println("Server: A new task has arrived");
        executor.execute(task);
        println("""
            Server: Pool Size: ${executor.poolSize}
            Server: Active Count: ${executor.activeCount}
            Server: Task Count: ${executor.taskCount}
            Server: Completed Tasks: ${executor.completedTaskCount}
        """.trimIndent());
    }

    /**
     * this method shuts down the executor
     */
    fun endServer() {
        executor.shutdown();
    }
}