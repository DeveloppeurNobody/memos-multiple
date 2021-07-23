package threads.cookbook_java9.ch04_thread_executor.recipe01_creating_executor_and_controlling_rejected_tasks.task

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor

class RejectedTaskController : RejectedExecutionHandler{

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

