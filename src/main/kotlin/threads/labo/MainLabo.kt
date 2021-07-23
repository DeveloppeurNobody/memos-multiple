package threads.labo

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor

object MainLabo {
    @JvmStatic
    fun main(args: Array<String>) {
        println("${Thread.currentThread().name}");


        val controlConnectionService = ControlConnectionService();
        controlConnectionService.executeTask(Task("Connection"))
        controlConnectionService.executeTask(Task("LIST"))
        controlConnectionService.executeTask(Task("CWD"))

    }
}



class RejectedTaskController : RejectedExecutionHandler {

    /**
     * this method that will be executed for each rejected task
     *
     * @param r Task that has been rejected
     * @param executor Executor that has rejected the task
     */
    override fun rejectedExecution(r: Runnable?, executor: ThreadPoolExecutor?) {
        println("""
            RejectedTaskController: The task $r has been rejected
            RejectedTaskController: $executor
            RejectedTaskController: Terminating: ${executor?.isTerminating}
            RejectedTaskController: Terminated: ${executor?.isTerminated}
        """.trimIndent());
    }
}

