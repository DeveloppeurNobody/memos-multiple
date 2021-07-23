package threads.labo

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class ControlConnectionService {
    /**
     * Create the executor
     */

    private val executor = Executors.newSingleThreadExecutor(ThreadFactory { it -> MyThread(it,"CC") });

    class MyThread(r: Runnable, name: String) : Thread(r, name) {
        init {
            println("MyThread() #newInstance");
        }
    }


    init {
        // Create the contoller for the Rejected tasks
        val controller: RejectedTaskController = RejectedTaskController();
    }

    /**
     * this method is called when a request to the server is made.
     * The server uses the executor to execute the request that it receives
     *
     * @param task The request made to the server
     */
    fun executeTask(task: Task) {
        println("ControlConnectionService: A new task has arrived");

        executor.submit(task)
    }

    /**
     * this method shuts down the executor
     */
    fun endServer() {
        println("endServer() shutdown");
        executor.shutdown();
    }
}