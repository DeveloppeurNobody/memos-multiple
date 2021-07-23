package coroutines

import kotlinx.coroutines.*

object AppCoroutines02_Job_Join {

    // runBlocking function as root coroutine
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        println("Start");

        // creating a job and start job
        val job = launch {
            println("  Starting job");
            delay(2000);
            helloSuspend();
            println("  Ending job");
        }
        println("Finished");

        // wait for job to finish
        job.join();
    }

    suspend fun helloSuspend() {
        println("Hello Suspending Functions");
    }
}